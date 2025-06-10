package web.mvc.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.domain.Bank;
import web.mvc.dto.RequestTransferDTO;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.BankRepository;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class BankServiceImpl implements  BankService{

    private final BankRepository bankRepository; // Spring Data JPA 가 구현객체를 생성해서 주입


    @Override
    public int transfer(RequestTransferDTO requestTransferDTO) throws BasicException {
        //출금 계좌에서 금액만큼 인출하기 -
       Bank outbank= bankRepository.findById(requestTransferDTO.getOutAccount()).orElseThrow(()->new BasicException(ErrorCode.FAILED_WITHDRAWAL_ACCOUNT));
        outbank.setBalance(outbank.getBalance()-requestTransferDTO.getAmount());
        //입금계좌에 금액만큼 입금하기
       Bank inbank= bankRepository.findById(requestTransferDTO.getInAccount()).orElseThrow(()->new BasicException(ErrorCode.FAILED_DEPOSIT_ACCOUNT));
        inbank.setBalance(inbank.getBalance()+requestTransferDTO.getAmount());
       //잔액확인
        if(inbank.getBalance()>1000) throw new BasicException(ErrorCode.FAILED_MAXIMUM);

        return 1;
    }
}
