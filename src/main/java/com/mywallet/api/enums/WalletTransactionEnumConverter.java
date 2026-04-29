package com.mywallet.api.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WalletTransactionEnumConverter implements AttributeConverter<WalletTransactionEnum, Short> {

    @Override
    public Short convertToDatabaseColumn(WalletTransactionEnum attribute) {
        return (attribute == null) ? null : (short) attribute.value;
    }

    @Override
    public WalletTransactionEnum convertToEntityAttribute(Short dbData) {
        if (dbData == null) return null;
        for (WalletTransactionEnum type : WalletTransactionEnum.values()) {
            if (type.value == dbData.intValue()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}
