package com.ragnarock.musicrecommends.exceptions;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidError {

    private HttpStatus status;
    Map<String, String> message;

}
