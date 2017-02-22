package pers.nelon.wtflogger;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static pers.nelon.wtflogger.WtfLog.Logger;

/**
 * Created by 李冰锋 on 2016/9/13 16:53.
 * E-mail:libf@ppfuns.com
 * Package: com.ppfuns.wtflogger
 */
public class BeanPrinter extends AbstractPrinter {
    public final static String TAG = BeanPrinter.class.getSimpleName();
    private final Object mBean;

    public BeanPrinter(Logger logger, Object bean) {
        super(logger);
        mBean = bean;
    }

    public void parse() {
        String bean = parseBean(mBean);
        getLogger().addBeanStr(bean);
    }

    private String parseBean(Object bean) {
        Class<?> aClass = bean.getClass();
        String beanStr = "";
        List<String> iteratedFieldList = new ArrayList<>();

        String beanClassName = aClass.getName();
        int hashCode = bean.hashCode();

        beanStr += "\t> BEAN : " + beanClassName + "\n";
        beanStr += "\t\t> OBJ : " + hashCode + "\n";

        /**
         * 先遍历getter方法
         */
        Method[] methods = aClass.getMethods();
        String beanVal = null;
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0 && method.getName().startsWith("get")) {
                /**
                 * getClass 滤除
                 */
                if (method.getName().equals("getClass")) {
                    continue;
                }

                try {
                    Object invoke = method.invoke(bean);
                    if (beanVal == null) {
                        beanVal = "\t\t> VALUE:\n";
                    }
                    String fieldName = method.getName().substring("get".length());
                    fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
                    iteratedFieldList.add(fieldName);
                    beanVal += "\t\t\t> " + fieldName + " : " + invoke + "\n";
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        }


        /**
         * 在遍历field成员
         */
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            String name = field.getName();
            if (iteratedFieldList.contains(name)) {
                continue;
            }

            try {
                Object o = field.get(bean);
                if (beanVal == null) {
                    beanVal = "\t\t> VALUE:\n";
                }
                beanVal += "\t\t\t> " + field.getName() + " : " + o + "\n";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        if (!TextUtils.isEmpty(beanVal)) {
            beanStr += beanVal;
        }

        return beanStr;
    }


}
