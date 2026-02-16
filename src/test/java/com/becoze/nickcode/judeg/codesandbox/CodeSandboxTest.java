package com.becoze.nickcode.judeg.codesandbox;

import com.becoze.nickcode.judeg.codesandbox.impl.DemoCodeSandbox;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeResponse;
import com.becoze.nickcode.model.enums.ProblemSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codesandbox.codeSandboxName:DemoCodeSandbox}")
    private String codeSandboxName;

    @Test
    void executeCode() {
        CodeSandbox codeSandbox = new DemoCodeSandbox();
        String code = "int main() {}";
        String language = ProblemSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }
    @Test
    void executeCodeByValue() {
        CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandboxInstance(codeSandboxName);
        String code = "int main() {}";
        String language = ProblemSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String codeSandboxName = scanner.next();
            CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandboxInstance(codeSandboxName);

            String code = "int main() {}";
            String language = ProblemSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
//            Assertions.assertNotNull(executeCodeResponse);

        }
    }
}