package com.hfridland.currencyconverter

object CurrencyCodes {
    data class Currency(val code: String, val descr: String)

    val data = init()

    private fun init(): Map<String, Currency> {
        return mapOf<String, Currency>(
                "AED" to Currency("AED", "United Arab Emirates Dirham"),
                "ARS" to Currency("ARS", "Argentine Peso"),
                "AUD" to Currency("AUD", "Australian Dollar"),
                "BGN" to Currency("BGN", "Bulgarian Lev"),
                "BND" to Currency("BND", "Brunei Dollar"),
                "BOB" to Currency("BOB", "Bolivian Boliviano"),
                "BRL" to Currency("BRL", "Brazilian Real"),
                "CAD" to Currency("CAD", "Canadian Dollar"),
                "CHF" to Currency("CHF", "Swiss Franc"),
                "CLP" to Currency("CLP", "Chilean Peso"),
                "CNY" to Currency("CNY", "Chinese Yuan Renminbi"),
                "COP" to Currency("COP", "Colombian Peso"),
                "CZK" to Currency("CZK", "Czech Republic Koruna"),
                "DKK" to Currency("DKK", "Danish Krone"),
                "EGP" to Currency("EGP", "Egyptian Pound"),
                "EUR" to Currency("EUR", "Euro"),
                "FJD" to Currency("FJD", "Fijian Dollar"),
                "GBP" to Currency("GBP", "British Pound Sterling"),
                "HKD" to Currency("HKD", "Hong Kong Dollar"),
                "HRK" to Currency("HRK", "Croatian Kuna"),
                "HUF" to Currency("HUF", "Hungarian Forint"),
                "IDR" to Currency("IDR", "Indonesian Rupiah"),
                "ILS" to Currency("ILS", "Israeli New Sheqel"),
                "INR" to Currency("INR", "Indian Rupee"),
                "JPY" to Currency("JPY", "Japanese Yen"),
                "KES" to Currency("KES", "Kenyan Shilling"),
                "KRW" to Currency("KRW", "South Korean Won"),
                "LTL" to Currency("LTL", "Lithuanian Litas"),
                "MAD" to Currency("MAD", "Moroccan Dirham"),
                "MXN" to Currency("MXN", "Mexican Peso"),
                "MYR" to Currency("MYR", "Malaysian Ringgit"),
                "NOK" to Currency("NOK", "Norwegian Krone"),
                "NZD" to Currency("NZD", "New Zealand Dollar"),
                "PEN" to Currency("PEN", "Peruvian Nuevo Sol"),
                "PHP" to Currency("PHP", "Philippine Peso"),
                "PKR" to Currency("PKR", "Pakistani Rupee"),
                "PLN" to Currency("PLN", "Polish Zloty"),
                "RON" to Currency("RON", "Romanian Leu"),
                "RSD" to Currency("RSD", "Serbian Dinar"),
                "RUB" to Currency("RUB", "Russian Ruble"),
                "SAR" to Currency("SAR", "Saudi Riyal"),
                "SEK" to Currency("SEK", "Swedish Krona"),
                "SGD" to Currency("SGD", "Singapore Dollar"),
                "THB" to Currency("THB", "Thai Baht"),
                "TRY" to Currency("TRY", "Turkish Lira"),
                "TWD" to Currency("TWD", "New Taiwan Dollar"),
                "UAH" to Currency("UAH", "Ukrainian Hryvnia"),
                "USD" to Currency("USD", "US Dollar"),
                "VEF" to Currency("VEF", "Venezuelan Bolí­var Fuerte"),
                "VND" to Currency("VND", "Vietnamese Dong"),
                "ZAR" to Currency("ZAR", "South African Ran")
        )
    }


}
