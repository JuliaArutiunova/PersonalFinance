package by.it_academy.jd2.service.utils;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;
import by.it_academy.jd2.dto.RecalculationDto;
import by.it_academy.jd2.service.api.IClientService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MoneyOperator {

    private final IClientService clientService;
    private static final int SCALE = 2;

    public MoneyOperator(IClientService clientService) {
        this.clientService = clientService;
    }

    public BigDecimal calculateBalance(BigDecimal balance, BigDecimal value,
                                       String accountCurrency, String operationCurrency) {
        if (accountCurrency.equals(operationCurrency)) {
            return balance.add(value)
                    .setScale(SCALE, RoundingMode.HALF_UP);
        } else {
            BigDecimal convertedValue = convertValue(value, operationCurrency, accountCurrency);
            return balance.add(convertedValue)
                    .setScale(SCALE, RoundingMode.HALF_UP);
        }

    }

    public BigDecimal convertBalanceAmount(BigDecimal balanceValue, String newCurrency, String accountCurrency) {
        BigDecimal convertedValue = convertValue(balanceValue, newCurrency, accountCurrency);
        return convertedValue.setScale(SCALE, RoundingMode.HALF_UP);
    }


    public BigDecimal recalculateBalance(RecalculationDto recalculationDTO) {
        String oldCurrency = recalculationDTO.getOldCurrency();
        String newCurrency = recalculationDTO.getNewCurrency();
        String accountCurrency = recalculationDTO.getAccountCurrency();

        if (oldCurrency.equals(newCurrency) && oldCurrency.equals(accountCurrency)) {
            return recalculateBalanceWithSameCurrency(recalculationDTO.getAccountBalance(),
                    recalculationDTO.getOldValue(), recalculationDTO.getNewValue());

        } else if (!oldCurrency.equals(newCurrency) && !oldCurrency.equals(accountCurrency)) {
            return recalculateBalanceWithDifferentCurrencies(recalculationDTO);

        } else {
            return recalculateBalanceWithMixedCurrencies(recalculationDTO);
        }

    }

    public BigDecimal refundAmount(BigDecimal balance, BigDecimal value,
                                   String accountCurrency, String operationCurrency) {
        if (accountCurrency.equals(operationCurrency)) {
            return balance.subtract(value)
                    .setScale(SCALE, RoundingMode.HALF_UP);
        } else {
            BigDecimal convertedValue = convertValue(value, operationCurrency, accountCurrency);
            return balance.subtract(convertedValue)
                    .setScale(SCALE, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal convertValue(BigDecimal value, String fromCurrency, String toCurrency) {
        BigDecimal exchangeRate = fetchExchangeRate(fromCurrency, toCurrency);
        return value.multiply(exchangeRate);
    }

    private BigDecimal fetchExchangeRate(String fromCurrency, String toCurrency) {
        ExchangeRateInfo exchangeRateInfo =
                clientService.getExchangeRate(fromCurrency, toCurrency);
        return exchangeRateInfo.getData().get(toCurrency).getValue();
    }

    private BigDecimal recalculateBalanceWithSameCurrency(BigDecimal currentBalance, BigDecimal oldOperationValue,
                                                          BigDecimal newOperationValue) {
        return currentBalance
                .add(newOperationValue.subtract(oldOperationValue))
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

     private BigDecimal recalculateBalanceWithDifferentCurrencies(RecalculationDto recalculationDto) {
        String newCurrency = recalculationDto.getNewCurrency();
        String accountCurrency = recalculationDto.getAccountCurrency();

        ExchangeRateInfo exchangeRateInfo =
                clientService.getExchangeRate(recalculationDto.getOldCurrency(), accountCurrency, newCurrency);

        BigDecimal oldCurrencyExchangeRate =
                exchangeRateInfo.getData().get(accountCurrency).getValue();

        BigDecimal newToOldRate =
                exchangeRateInfo.getData().get(newCurrency).getValue();

        BigDecimal newCurrencyExchangeRate =
                oldCurrencyExchangeRate.divide((newToOldRate), RoundingMode.HALF_UP);

        return recalculationDto.getAccountBalance()
                .add((recalculationDto.getNewValue().multiply(newCurrencyExchangeRate))
                        .subtract(recalculationDto.getOldValue().multiply(oldCurrencyExchangeRate)))
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal recalculateBalanceWithMixedCurrencies(RecalculationDto recalculationDTO) {
        BigDecimal exchangeRate =
                fetchExchangeRate(recalculationDTO.getNewCurrency(), recalculationDTO.getAccountCurrency());

        return recalculationDTO.getAccountBalance()
                .add((recalculationDTO.getNewValue().multiply(exchangeRate))
                        .subtract(recalculationDTO.getOldValue().multiply(exchangeRate)))
                .setScale(SCALE, RoundingMode.HALF_UP);
    }


}
