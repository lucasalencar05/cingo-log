package br.com.cingo.domain.component;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class ReadFileComponent {

    private String getLine;
    private int count = 0;

    private List<String> getFile() {
        List<String> readFileList = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("cingohc.log");
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);
            Stream<String> stringStream = data.lines();
            stringStream.forEach(readFileList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFileList;
    }


    public List<String> getAndReadFile() {
        List<String> readFileList = getFile();
        Map<Integer, String> mapLines = new HashMap<>();
        String pipe = "| ";
        readFileList.forEach(ln -> {
            if (ln.indexOf(pipe) > 0) {
                count++;
                mapLines.put(count, ln.substring(ln.indexOf(pipe) + 2));
                getLine = ln.substring(ln.indexOf(pipe) + 2);
            } else {
                mapLines.put(count, getLine + "\n" + ln);
                getLine = getLine + "\n" + ln;
            }
        });

        List<String> readLineList = new ArrayList<>();
        mapLines.forEach((k, v) -> readLineList.add(v));
        return readLineList;
    }
}
