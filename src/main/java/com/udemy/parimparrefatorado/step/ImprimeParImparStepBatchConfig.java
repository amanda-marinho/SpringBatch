package com.udemy.parimparrefatorado.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ImprimeParImparStepBatchConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step parImparStep() {
        return stepBuilderFactory
                .get("parImparStep2")
                .<Integer, String>chunk(1)
                .reader(contaAteDezReader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    public IteratorItemReader<Integer> contaAteDezReader() {
        List<Integer> numerosDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<Integer>(numerosDeUmAteDez.iterator());
    }

    public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<Integer, String>
                (item -> item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é Ímpar", item));
    }

    public ItemWriter<String> imprimeWriter() {
        return itens -> itens.forEach(System.out::println);
    }
}
