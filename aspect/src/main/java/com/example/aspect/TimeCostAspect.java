package com.example.aspect;

import android.os.SystemClock;
import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TimeCostAspect {

    private static final String TAG = "sansui";
    private static final String POINTCUT_METHOD = "execution(* *..btnClick(..))";


    @Pointcut(POINTCUT_METHOD)
    public void timeCostPointCut() {}

    @Around("timeCostPointCut()")
    public void aroundTimeCost(ProceedingJoinPoint joinPoint) throws Throwable {
        //拿到方法的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        //方法名
        String methodName = methodSignature.getName();
        final long start = SystemClock.elapsedRealtime();
        joinPoint.proceed();
        final long time = SystemClock.elapsedRealtime() - start;
        Log.i(TAG, "fun:" + methodName + " timecost=" + time);
    }
}
