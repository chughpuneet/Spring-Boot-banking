package com.banking.accounts.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @RequiredArgsConstructor
public class Properties {
    private final String msg;
    private final String buildVersion;
    private final Map<String, String> mailDetails;
    private final List<String> activeBranches;
}
