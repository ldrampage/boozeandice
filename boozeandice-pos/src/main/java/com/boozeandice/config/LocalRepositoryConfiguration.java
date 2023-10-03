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
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import com.boozeandice.local.entity.Address;
//import com.boozeandice.local.entity.CashAdded;
//import com.boozeandice.local.entity.CashDrawer;
//import com.boozeandice.local.entity.Customer;
//import com.boozeandice.local.entity.Expense;
//import com.boozeandice.local.entity.JobPosition;
//import com.boozeandice.local.entity.POSConfig;
//import com.boozeandice.local.entity.Product;
//import com.boozeandice.local.entity.ProductCategory;
//import com.boozeandice.local.entity.ProductStock;
//import com.boozeandice.local.entity.Role;
//import com.boozeandice.local.entity.Shipment;
//import com.boozeandice.local.entity.TimeRecord;
//import com.boozeandice.local.entity.Transaction;
//import com.boozeandice.local.entity.TransactionItem;
//import com.boozeandice.local.entity.User;
//import com.boozeandice.local.entity.UserActivityLog;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages={"com.boozeandice.local"}, 
//transactionManagerRef = "localTransactionManager", entityManagerFactoryRef = "localEntityManagerFactory")
//public class LocalRepositoryConfiguration {
//	
//	@Bean
//	@Primary
//	@ConfigurationProperties("spring.local-datasource")
//	public DataSourceProperties localDatasourceProperties() {
//		return new DataSourceProperties();
//	}
//	
//	@Primary
//	@Bean
//	public DataSource localDataSource() {
//		return localDatasourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//	}
//	
//	@Primary
//	@Bean
//	public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//		return builder.dataSource(this.localDataSource()).packages(Address.class, CashAdded.class, CashDrawer.class, 
//				Customer.class, Expense.class, JobPosition.class, POSConfig.class, Product.class, ProductCategory.class, 
//				ProductStock.class, Role.class, Shipment.class, TimeRecord.class, Transaction.class, 
//				TransactionItem.class, User.class, UserActivityLog.class).build();
//	}
//	
//	@Primary
//	@Bean
//	public PlatformTransactionManager localTransactionManager(final @Qualifier("localEntityManagerFactory") LocalContainerEntityManagerFactoryBean lcemBean ) {
//		JpaTransactionManager jpa = new JpaTransactionManager();
//		jpa.setEntityManagerFactory(lcemBean.getObject());
//		return jpa;
//	}
//	
//	
//	
//
//}
