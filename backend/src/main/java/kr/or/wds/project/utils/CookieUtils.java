package kr.or.wds.project.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    public static void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAgeSeconds,
            boolean httpOnly,
            boolean secure,
            String sameSite,   // "Lax", "Strict", "None"
            String path,       // 기본 "/"
            String domain      // null 가능
    ) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(httpOnly)
                .secure(secure)
                .path(path != null ? path : "/")
                .maxAge(maxAgeSeconds)
                .sameSite(sameSite != null ? sameSite : "Lax")
                .domain(domain != null && !domain.isBlank() ? domain : null).build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void deleteCookie(
            HttpServletResponse response,
            String name,
            boolean secure,
            String sameSite,
            String path,
            String domain
    ) {
        // 빈 값 + Max-Age=0 로 즉시 만료
        addCookie(response, name, "", 0, true, secure, sameSite, path, domain);
    }

    public static Optional<String> getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .map(Cookie::getValue)
                .findFirst();
    }

}