import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Annotation[] annotations = Customer.class.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals("Table")) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                Method[] methods = annotationType.getDeclaredMethods();
                StringBuilder annotationDetails = new StringBuilder();
                annotationDetails.append("Anotação: ").append(annotationType.getSimpleName()).append(", Parâmetro: ");
                for (Method method : methods) {
                    if (method.getParameterCount() == 0) {
                        try {
                            Object value = method.invoke(annotation);
                            annotationDetails.append(value).append(", ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                annotationDetails.delete(annotationDetails.length() - 2, annotationDetails.length());
                System.out.println(annotationDetails);
            }
        }
    }
}