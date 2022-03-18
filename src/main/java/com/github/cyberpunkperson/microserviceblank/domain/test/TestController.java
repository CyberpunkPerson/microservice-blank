package com.github.cyberpunkperson.microserviceblank.domain.test;

import com.github.cyberpunkperson.microserviceblank.controller.TestsApiDelegate;
import com.github.cyberpunkperson.microserviceblank.model.GetTestsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.ResponseEntity.ok;

@Component
class TestController implements TestsApiDelegate {

    @Override
    public ResponseEntity<GetTestsResponse> getTests() {
        return ok(new GetTestsResponse().payload("Hello from test controller"));
    }
}
