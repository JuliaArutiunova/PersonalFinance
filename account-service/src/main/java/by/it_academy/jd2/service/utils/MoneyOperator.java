package by.it_academy.jd2.service.utils;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;
import by.it_academy.jd2.dto.RecalculationDto;
import by.it_academy.jd2.service.api.IClientService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class MoneyOperator {

    private final IClientService clientService;

    public MoneyOperator(IClientService clientService) {
        this.clientService = clientService;
    }

    public BigDecimal calculateBalance(BigDecimal balance, BigDecimal value,
                                       UUID accountCurrencyId, UUID operationCurrencyId) {
        if (accountCurrencyId.equals(operationCurrencyId)) {
            return balance.add(value)
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            BigDecimal convertedValue = convertValue(value, operationCurrencyId, accountCurrencyId);
            return balance.add(convertedValue)
                    .setScale(2, RoundingMode.HALF_UP);
        }

    }

    public BigDecimal convertBalanceAmount(BigDecimal value, UUID currencyId, UUID accountCurrencyId) {
        BigDecimal convertedValue = convertValue(value, currencyId, accountCurrencyId);
        return convertedValue.setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal recalculateBalance(RecalculationDto recalculationDTO) {
        BigDecimal balance = recalculationDTO.getAccountBalance();
        BigDecimal oldOperationValue = recalculationDTO.getOldValue();
        BigDecimal newOperationValue = recalculationDTO.getNewValue();

        UUID oldCurrencyId = recalculationDTO.getOldCurrency();
        UUID newCurrencyId = recalculationDTO.getNewCurrency();
        UUID accountCurrencyId = recalculationDTO.getAccountCurrency();

        if (oldCurrencyId.equals(newCurrencyId) && oldCurrencyId.equals(accountCurrencyId)) {
            return balance
                    .add(newOperationValue.subtract(oldOperationValue))
                    .setScale(2, RoundingMode.HALF_UP);

        } else if (!oldCurrencyId.equals(newCurrencyId) && !oldCurrencyId.equals(accountCurrencyId)) {
            Map<UUID, String> currencyInfo =
                    clientService.getCurrencyNames(oldCurrencyId, accountCurrencyId, newCurrencyId);

            ExchangeRateInfo exchangeRateInfo =
                    clientService.getExchangeRate(currencyInfo.get(oldCurrencyId),
                            currencyInfo.get(accountCurrencyId), currencyInfo.get(newCurrencyId));

            BigDecimal oldCurrencyExchangeRate =
                    exchangeRateInfo.getData().get(currencyInfo.get(accountCurrencyId)).getValue();

            BigDecimal newToOldRate =
                    exchangeRateInfo.getData().get(currencyInfo.get(newCurrencyId)).getValue();

            BigDecimal newCurrencyExchangeRate =
                    oldCurrencyExchangeRate.divide((newToOldRate), RoundingMode.HALF_UP);

            return balance
                    .add((newOperationValue.multiply(newCurrencyExchangeRate))
                            .subtract(oldOperationValue.multiply(oldCurrencyExchangeRate)))
                    .setScale(2, RoundingMode.HALF_UP);

        } else {
            BigDecimal exchangeRate = fetchExchangeRate(newCurrencyId, accountCurrencyId);
            return balance
                    .add((newOperationValue.multiply(exchangeRate))
                            .subtract(oldOperationValue.multiply(exchangeRate)))
                    .setScale(2, RoundingMode.HALF_UP);
        }

    }

    public BigDecimal refundAmount(BigDecimal balance, BigDecimal value,
                                   UUID accountCurrencyId, UUID operationCurrencyId) {
        if (accountCurrencyId.equals(operationCurrencyId)) {
            return balance.subtract(value)
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            BigDecimal convertedValue = convertValue(value, operationCurrencyId, accountCurrencyId);
            return balance.subtract(convertedValue)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal convertValue(BigDecimal value, UUID fromCurrencyId, UUID toCurrencyId) {
        BigDecimal exchangeRate = fetchExchangeRate(fromCurrencyId, toCurrencyId);
        return value.multiply(exchangeRate);
    }

    public BigDecimal fetchExchangeRate(UUID fromCurrencyId, UUID toCurrencyId){
        Map<UUID, String> currencyInfo = clientService.getCurrencyNames(fromCurrencyId, toCurrencyId);
        ExchangeRateInfo exchangeRateInfo =
                clientService.getExchangeRate(currencyInfo.get(fromCurrencyId), currencyInfo.get(toCurrencyId));
        return exchangeRateInfo.getData().get(currencyInfo.get(toCurrencyId)).getValue();
    }


}
