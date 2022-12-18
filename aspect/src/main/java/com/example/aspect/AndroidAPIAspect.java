package com.example.aspect;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class AndroidAPIAspect {
    private static final String TAG = "sansui";

    //表示匹配调用Toast及其子类调用的show方法，不论返回类型以及参数列表
    @Pointcut("call(* android.widget.Toast+.show(..))")
    public void toastShow() {
    }

    @Before("toastShow()")
    public void beforeToastShow(JoinPoint joinPoint) {
        //拿到方法的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        //方法名
        String methodName = methodSignature.getName();
        Log.i(TAG, "beforeToastShow fun:" + methodName + " className=" + className);
    }
}
