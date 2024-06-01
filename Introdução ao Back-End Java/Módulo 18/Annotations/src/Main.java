import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        System.out.println("Anotações da classe Customer:");
        Annotation[] annotations = Customer.class.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Retention.class) && annotationType.getAnnotation(Retention.class).value() == RetentionPolicy.RUNTIME) {
                Method[] methods = annotationType.getDeclaredMethods();

                StringBuilder annotationDetails = new StringBuilder();
                annotationDetails.append("Anotação: ").append(annotationType.getSimpleName()).append(", Parâmetros: {");

                for (Method method : methods) {
                    if (method.getParameterCount() == 0) {
                        try {
                            Object value = method.invoke(annotation);
                            annotationDetails.append(method.getName()).append("=").append(value).append(", ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                annotationDetails.delete(annotationDetails.length() - 2, annotationDetails.length());
                annotationDetails.append("}");

                System.out.println(annotationDetails);
            }
        }
    }
}