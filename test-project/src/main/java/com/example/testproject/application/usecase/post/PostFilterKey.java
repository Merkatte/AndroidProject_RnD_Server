package com.example.testproject.application.usecase.post;

public enum PostFilterKey {
    /**
     * 검색 필터
     * 사용자 닉네임 검색
     * 게시글 카테고리 검색
     * 게시글 내용 검색
     */
    CONTENTS("contents"),
    CATEGORY("category"),
    USERNAME("username");

    private final String value;

    PostFilterKey(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
