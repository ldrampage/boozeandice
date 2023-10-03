//package com.boozeandice.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import com.boozeandice.cloud.entity.CloudAddress;
//import com.boozeandice.cloud.entity.CloudCashAdded;
//import com.boozeandice.cloud.entity.CloudCashDrawer;
//import com.boozeandice.cloud.entity.CloudCustomer;
//import com.boozeandice.cloud.entity.CloudExpense;
//import com.boozeandice.cloud.entity.CloudJobPosition;
//import com.boozeandice.cloud.entity.CloudPOSConfig;
//import com.boozeandice.cloud.entity.CloudProduct;
//import com.boozeandice.cloud.entity.CloudProductCategory;
//import com.boozeandice.cloud.entity.CloudProductStock;
//import com.boozeandice.cloud.entity.CloudRole;
//import com.boozeandice.cloud.entity.CloudShipment;
//import com.boozeandice.cloud.entity.CloudTimeRecord;
//import com.boozeandice.cloud.entity.CloudTransaction;
//import com.boozeandice.cloud.entity.CloudTransactionItem;
//import com.boozeandice.cloud.entity.CloudUser;
//import com.boozeandice.cloud.entity.CloudUserActivityLog;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages={"com.boozeandice.cloud"}, 
//transactionManagerRef = "cloudTransactionManager", entityManagerFactoryRef = "cloudEntityManagerFactory")
//public class CloudRepositoryConfiguration {
//	
//	@Bean
//	@ConfigurationProperties("spring.cloud-datasource")
//	public DataSourceProperties cloudDatasourceProperties() {
//		return new DataSourceProperties();
//	}
//	
//	@Bean
//	public DataSource cloudDataSource() {
//		return cloudDatasourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//	}
//	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean cloudEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//		return builder.dataSource(this.cloudDataSource()).packages(CloudAddress.class, CloudCashAdded.class, CloudCashDrawer.class, 
//				CloudCustomer.class, CloudExpense.class, CloudJobPosition.class, CloudPOSConfig.class, CloudProduct.class, CloudProductCategory.class, 
//				CloudProductStock.class, CloudRole.class, CloudShipment.class, CloudTimeRecord.class, CloudTransaction.class, 
//				CloudTransactionItem.class, CloudUser.class, CloudUserActivityLog.class).build();
//		
//	}
//	
//	@Bean
//	public PlatformTransactionManager cloudTransactionManager(final @Qualifier("cloudEntityManagerFactory") LocalContainerEntityManagerFactoryBean lcemBean ) {
//		JpaTransactionManager jpa = new JpaTransactionManager();
//		jpa.setEntityManagerFactory(lcemBean.getObject());
//		return jpa;
//	}
//
//}
