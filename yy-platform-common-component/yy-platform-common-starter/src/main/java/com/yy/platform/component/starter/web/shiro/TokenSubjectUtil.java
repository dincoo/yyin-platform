package com.yy.platform.component.starter.web.shiro;

import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 改造通过cache 获取 subject
 */
public class TokenSubjectUtil {

    private static Map<String, Subject> subjectMap = new HashMap<>();

    public static void saveSubject(String randomKey,Subject sub) {
        subjectMap.put(randomKey, sub);
    }

    public static Subject getSubject(String key) {
        return subjectMap.get(key);
    }
}
