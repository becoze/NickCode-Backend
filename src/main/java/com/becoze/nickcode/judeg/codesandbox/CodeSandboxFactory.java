package com.becoze.nickcode.judeg.codesandbox;

import com.becoze.nickcode.judeg.codesandbox.impl.DemoCodeSandbox;
import com.becoze.nickcode.judeg.codesandbox.impl.RemoteCodeSandbox;
import com.becoze.nickcode.judeg.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * CodeSandbox Factory. Create a specified code sandbox instance based on the String parameter input.
 */
public class CodeSandboxFactory {
    /**
     * Create CodeSandbox instance
     *
     * @param codeSandboxName
     * @return
     */
    public static CodeSandbox createCodeSandboxInstance(String codeSandboxName) {
        switch (codeSandboxName) {
            case "DemoCodeSandbox":
                return new DemoCodeSandbox();
            case "RemoteCodeSandbox":
                return new RemoteCodeSandbox();
            case "ThirdPartyCodeSandbox":
                return new ThirdPartyCodeSandbox();
            default:
                return null;
        }
    }
}
