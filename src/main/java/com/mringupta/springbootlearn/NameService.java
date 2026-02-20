package com.mringupta.springbootlearn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NameService {
    @Value("${name.myname}")
    public String name;

    public String sendName() {
        return name;
    }
}
