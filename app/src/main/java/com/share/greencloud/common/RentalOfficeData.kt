package com.share.greencloud.common

import com.share.greencloud.domain.model.RentalOffice


class RentalOfficeData {
    companion object {
        private val list = listOf(
                RentalOffice(0,"강남 대여소", "",37.4987548, 127.027777,10),
                RentalOffice(1,"서초 대여소", "",37.4910180, 127.006799,15),
                RentalOffice(2,"선릉 대여소", "",37.5052801, 127.049777,13),
                RentalOffice(3,"삼성 대여소", "",37.5100758, 127.063861,20)
        )

        @JvmStatic
        fun fetchOfficeInfo() = list
    }
}