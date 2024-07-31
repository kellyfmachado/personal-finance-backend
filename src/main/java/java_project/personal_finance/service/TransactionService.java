package java_project.personal_finance.service;

import java_project.personal_finance.dto.TransactionDto;
import java_project.personal_finance.model.CategoryModel;
import java_project.personal_finance.model.TransactionModel;
import java_project.personal_finance.repository.CategoryRepository;
import java_project.personal_finance.repository.TransactionRepository;
import java_project.personal_finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void addTransaction(TransactionModel transactionModel){
        transactionRepository.save(transactionModel);
    }

    public void updateTransaction(TransactionDto transactionDto){
        Optional<TransactionModel> optionalTransaction = transactionRepository.findById(transactionDto.getId());

        if (optionalTransaction.isPresent()) {
            TransactionModel transaction = optionalTransaction.get();
            transaction.setDate(transactionDto.getDate());
            transaction.setAmount(transactionDto.getAmount());
            transaction.setType(transactionDto.getType());
            transaction.setDescription(transactionDto.getDescription());

            Optional<CategoryModel> optionalCategory = categoryRepository.findById(transactionDto.getCategoryId());
            if (optionalCategory.isPresent()) {
                transaction.setCategoryModel(optionalCategory.get());
            } else {
                throw new RuntimeException("Category not found");
            }

            transactionRepository.save(transaction);

        } else {
            throw new RuntimeException("Transaction not found");
        }
    }

    public List<TransactionModel> listAll(){
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id){
        if (transactionRepository.existsById(id)){
            transactionRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Transaction not found");
        }
    }

}
