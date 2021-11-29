package com.nkuskov.epam_hw

import kotlin.math.pow

class IMBCalculator {
    enum class IMBType{
        PronouncedDeficit,
        Deficit,
        Normal,
        ExcessWeight,
        FirstDegree,
        SecondDegree,
        ThirdDegree
    }

    companion object{

        private val imbDataMap: List<IMBData> = listOf(IMBData(16.0, R.string.imb_weight_pronounced_deficit, IMBType.PronouncedDeficit),
                                                       IMBData(18.5, R.string.imb_weight_deficit, IMBType.Deficit),
                                                       IMBData(25.0, R.string.imb_normal, IMBType.Normal),
                                                       IMBData(30.0, R.string.imb_excess_weight, IMBType.ExcessWeight),
                                                       IMBData(35.0, R.string.imb_first_degree_obesity, IMBType.FirstDegree),
                                                       IMBData(40.0, R.string.imb_second_degree_obesity, IMBType.SecondDegree))
                                                       .sortedBy { it.imbCoefficient }

        fun getIMBData(weight: Double, height: Double) : IMBData{
            (weight/((height/100).pow(2))).let { imb ->
                imbDataMap.find { imbDataUnit -> imb < imbDataUnit.imbCoefficient }.let { imbData ->
                    return IMBData(imb, imbData?.resourceId ?: R.string.imb_third_degree_obesity, imbData?.imbType ?: IMBType.ThirdDegree)
                }
            }
        }
    }

    data class IMBData(val imbCoefficient: Double, val resourceId: Int, val imbType: IMBType)
}