package br.com.cingo.domain.service;

import br.com.cingo.domain.model.Log;
import br.com.cingo.domain.component.ReadFileComponent;
import br.com.cingo.domain.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ReadFileComponent readFileComponent;

    public List<Log> findAll() {
        return logRepository.findAll(Sort.by(Sort.Direction.DESC, "quantity"));
    }

    @Transactional
    public Log save(Log log) {
        Optional<Log> logContent = logRepository.findByContent(log.getContent());
        if(logContent.isPresent()){
            logContent.get().incrementQuantity();
            return logContent.get();
        }
        return logRepository.save(log);
    }

    @Transactional
    public List<Log> saveAll(List<Log> logList) {
        return logRepository.saveAll(logList);
    }

    @PostConstruct
    @Transactional
    public List<Log> saveLogs() {
        List<Log> logList = new ArrayList<>();
        List<String> fileList = readFileComponent.getAndReadFile();
        Set<String> set = new HashSet<>();

        fileList.stream().filter(set::add).forEach(content -> {
            Long quantity = fileList.stream().filter(f-> f.equals(content)).count();
            Log newLog = new Log();
            newLog.setContent(content);
            newLog.setQuantity(quantity);
            logList.add(newLog);
        });

        return saveAll(logList);
    }


}
