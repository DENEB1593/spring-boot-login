package org.sample.login.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {

    private Status status;
    private T data;
    private String message;
    private LocalDateTime time;

    /**
     * 성공 시 응답 설정
     */
    public static <T> CommonResponse<T> success(T data) {
        return success(data, Strings.EMPTY);
    }

    @SuppressWarnings("unchecked")
    public static <T> CommonResponse<T> success(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .status(Status.SUCCESS)
                .data(data)
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }

    /**
     * 실패 시 응답 설정
     */

    public static <T> CommonResponse<T> fail(T data) {
        return fail(data, Strings.EMPTY);
    }

    @SuppressWarnings("unchecked")
    public static <T> CommonResponse<T> fail(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .status(Status.FAIL)
                .data(data)
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }

    @Getter
    public enum Status {
        SUCCESS, FAIL
    }

}
