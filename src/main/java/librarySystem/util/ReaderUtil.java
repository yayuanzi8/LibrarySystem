package librarySystem.util;

import librarySystem.domain.Reader;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpSession;

/**
 * Created by yayuanzi8 on 2017/5/19 0019.
 */
public class ReaderUtil {
    public static Reader getUserFromSecurityContext(HttpSession httpSession) {
        SecurityContext context = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        return (Reader) context.getAuthentication().getPrincipal();
    }
}
