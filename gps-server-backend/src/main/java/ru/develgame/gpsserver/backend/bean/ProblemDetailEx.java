package ru.develgame.gpsserver.backend.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

@Getter
@Setter
public class ProblemDetailEx extends ProblemDetail {
    private int errorCode;

    public ProblemDetailEx(int rawStatusCode, int errorCode) {
        super(rawStatusCode);
        this.errorCode = errorCode;
    }

    public ProblemDetailEx(int rawStatusCode, int errorCode, String detail) {
        super(rawStatusCode);
        this.errorCode = errorCode;
        this.setDetail(detail);
    }
}
