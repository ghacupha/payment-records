package io.github.erp.internal.batch.payments;

import com.google.common.collect.ImmutableList;
import io.github.erp.config.FileUploadsProperties;
import io.github.erp.internal.Mapping;
import io.github.erp.internal.excel.ExcelFileDeserializer;
import io.github.erp.internal.model.PaymentEVM;
import io.github.erp.internal.service.BatchService;
import io.github.erp.service.PaymentsFileUploadService;
import io.github.erp.service.dto.PaymentDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PaymentBatchConfig {
    public static final String JOB_NAME = "paymentsPersistenceJob";
    private static final String ITEM_WRITER_NAME = "paymentsItemWriter";
    private static final String ITEM_READER_NAME = "paymentsItemReader";
    private static final String ITEM_PROCESSOR_NAME = "paymentsItemProcessor";
    private static final String READER_STEP_NAME = "readPaymentListFromExcelFile";
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ExcelFileDeserializer<PaymentEVM> deserializer;

    @Autowired
    private PaymentsFileUploadService fileUploadService;

    @Autowired
    private FileUploadsProperties fileUploadsProperties;

    @Value("#{jobParameters['fileId']}")
    private static long fileId;

    @Value("#{jobParameters['startUpTime']}")
    private static long startUpTime;

    @Autowired
    private JobExecutionListener persistenceJobListener;

    @Autowired
    private BatchService<PaymentDTO> paymentDTOBatchService;

    @Autowired
    private Mapping<PaymentEVM, PaymentDTO> mapping;


    @Bean(JOB_NAME)
    public Job persistenceJob() {
        // @formatter:off
        return jobBuilderFactory.get(JOB_NAME)
            .preventRestart()
            .listener(persistenceJobListener)
            .incrementer(new RunIdIncrementer())
            .flow(readCurrencyListFromFile())
            .end()
            .build();
        // @formatter:on
    }

    @Bean(ITEM_WRITER_NAME)
    public ItemWriter<List<PaymentDTO>> itemWriter() {

        return items -> items.stream().peek(paymentDTOBatchService::save).forEach(paymentDTOBatchService::index);
    }

    @Bean(ITEM_PROCESSOR_NAME)
    public ItemProcessor<List<PaymentEVM>, List<PaymentDTO>> itemProcessor() {

        return evms -> evms.stream().map(mapping::toValue2).collect(ImmutableList.toImmutableList());
    }

    @Bean(READER_STEP_NAME)
    public Step readCurrencyListFromFile() {
        // @formatter:off
        return stepBuilderFactory.get(READER_STEP_NAME)
            .<List<PaymentEVM>, List<PaymentDTO>>chunk(2)
            .reader(itemReader(fileId))
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
        // @formatter:off
    }

    @Bean(ITEM_READER_NAME)
    @JobScope
    public ItemReader itemReader(@Value("#{jobParameters['fileId']}") long fileId) {

        return new PaymentListItemReader(deserializer, fileUploadService, fileId, fileUploadsProperties);
    }
}
