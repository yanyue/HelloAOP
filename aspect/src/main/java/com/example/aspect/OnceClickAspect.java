package com.example.aspect;

import android.util.Log;
import android.view.View;
import com.example.annotation.ClickOnce;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class OnceClickAspect {
    private static final String TAG = "sansui";
    private static final String POINTCUT_METHOD =
        "execution(@com.example.annotation.ClickOnce * *(..))";

    /**
     * 最近一次点击的时间
     */
    private long mLastTime;
    /**
     * 最近一次点击的控件ID
     */
    private int mLastId;

    @Pointcut(POINTCUT_METHOD)
    public void clickOnce() {}

    @Around("clickOnce() && @annotation(singleClick)")
    public void aroundClickOnce(ProceedingJoinPoint joinPoint, ClickOnce singleClick)
        throws Throwable {
        View v = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                v = (View) arg;
            }
        }
        if (v != null) {
            long currentTime = System.currentTimeMillis();
            if ( v.getId() == mLastId && currentTime - mLastTime < singleClick.value()) {
                Log.w(TAG, "忽略多余的点击事件");
                return;
            }
            mLastId = v.getId();
            mLastTime = currentTime;

            // 正常执行
            joinPoint.proceed();
        } else {
            Log.e(TAG, "这是不可能的");
        }
    }
}
