package sideproject.urkunde.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 일반적인 반환, 딱히 반환해야할게 없을때
 * ResponseEntity.ok()를 사용하는것보단
 * new ResponseEntity<>(GeneralResponse.of(HttpStatus.NO_CONTENT, "댓글 삭제에 성공하였습니다."), HttpStatus.NO_CONTENT);
 *
 */
@Getter
public class GeneralResponse {

    private final Long id;
    private final Integer code;
    private final String message;

    private GeneralResponse(Long id , int code, String message) {
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public static GeneralResponse of(Long id, HttpStatus httpStatus, String message) {
        return new GeneralResponse(id, httpStatus.value(), message);
    }
}
