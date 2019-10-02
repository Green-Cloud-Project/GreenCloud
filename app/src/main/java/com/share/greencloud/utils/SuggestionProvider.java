package com.share.greencloud.utils;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SuggestionProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        MatrixCursor cursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_TEXT_2,
                        SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA
                });

        //SearchView에 입력된 텍스트는 uri의 마지막 인자로 옵니다.
        String query = uri.getLastPathSegment().toLowerCase();
        String[] placeNames = {"강남역","잠실역"};
        int columnId = 0;

        for (String placeName : placeNames) {
            if (placeName.contains(query)) {
                String companyUrl = getPlaceDetail(placeName);

                //ArrauList 객체를 만들어서 MatrixCursor 객체에 row로 추가합니다.
                //주의하실 점이 MatrixCursor 객체를 만들때 지정했던 column의 순서에 맞게 해당 값을 추가하셔야합니다.
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(columnId));
                row.add(placeName);
                row.add(companyUrl);
                row.add(companyUrl);

                //만든 row를 MatrixCursor객체에 추가합니다.
                cursor.addRow(row);

                columnId++;
            }
        }

        return cursor;
    }

    private String getPlaceDetail(String companyName) {
        switch (companyName) {
            case "강남역":
                return "강남역 11번출구";
            case "잠심역":
                return "잠심역 4번출구";
            case "Microsoft":
                return "https://www.microsoft.com/ko-kr";
            case "Oracle":
                return "https://www.oracle.com/kr/index.html";
            case "Facebook":
                return "https://ko-kr.facebook.com/";
            case "Amazon":
                return "https://www.amazon.com/";
            case "Netflix":
                return "https://www.netflix.com/kr/";
            case "Twitter":
                return "https://twitter.com/?lang=ko";
            case "Kakao":
                return "https://www.kakaocorp.com/";
            case "Naver":
                return "https://www.naver.com/";
            case "AMD":
                return "https://www.amd.com/ko";
            case "Intel":
                return "https://www.intel.co.kr/content/www/kr/ko/homepage.html";
            case "Nvidia":
                return "www.nvidia.co.kr/Download/index.aspx?lang=kr";
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
