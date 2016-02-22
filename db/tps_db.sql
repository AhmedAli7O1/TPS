-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 22, 2016 at 10:00 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 7.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tps_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `ID` int(11) NOT NULL,
  `SALES` double NOT NULL,
  `DEBTS` double NOT NULL,
  `INCOMES` double NOT NULL,
  `OUTGOINGS` double NOT NULL,
  `WITHDRAWALS` double NOT NULL,
  `PURCHASES` double NOT NULL,
  `BALANCE` double NOT NULL,
  `PROFITS` double NOT NULL,
  `DATE` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`ID`, `SALES`, `DEBTS`, `INCOMES`, `OUTGOINGS`, `WITHDRAWALS`, `PURCHASES`, `BALANCE`, `PROFITS`, `DATE`) VALUES
(3, 1500, 0, 1000, 200, 300, 2000, 2000, 2000, '2016-01-01'),
(4, 4500, 0, 0, 0, 500, 0, 6000, 4000, '2016-02-01'),
(5, 0, 0, 500, 0, 0, 0, 7000, 500, '2016-03-01');

-- --------------------------------------------------------

--
-- Table structure for table `debts`
--

CREATE TABLE `debts` (
  `ID` int(11) NOT NULL,
  `VALUE` double NOT NULL,
  `PAID` double NOT NULL,
  `ORDER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `incomes`
--

CREATE TABLE `incomes` (
  `ID` int(11) NOT NULL,
  `DETAILS` varchar(300) NOT NULL,
  `VALUE` double NOT NULL,
  `DATE` date NOT NULL,
  `IS_DEBT` tinyint(1) NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `incomes`
--

INSERT INTO `incomes` (`ID`, `DETAILS`, `VALUE`, `DATE`, `IS_DEBT`, `ACCOUNT_ID`) VALUES
(3, 'fsdfsdf', 500, '2016-01-04', 1, 3),
(4, 'asdasdasd', 500, '2016-01-13', 0, 3),
(5, 'asdasd', 500, '2016-03-08', 1, 5);

--
-- Triggers `incomes`
--
DELIMITER $$
CREATE TRIGGER `INCOMES_ADD` AFTER INSERT ON `incomes` FOR EACH ROW BEGIN
SET @VALUE = NEW.VALUE ;
IF NEW.IS_DEBT > 0 THEN
	SET @PROF = NEW.VALUE;
ELSEIF NEW.IS_DEBT = 0 THEN
	SET @PROF = 0;
END IF; 
UPDATE ACCOUNTS SET INCOMES = INCOMES + @VALUE, BALANCE = BALANCE + @VALUE, PROFITS = PROFITS + @PROF WHERE ID = NEW.ACCOUNT_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `ID` int(11) NOT NULL,
  `CUSTOMER` varchar(200) NOT NULL,
  `PRICE` double NOT NULL,
  `PAID` double NOT NULL,
  `PURCHASES_VALUE` double NOT NULL,
  `DATE` date NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`ID`, `CUSTOMER`, `PRICE`, `PAID`, `PURCHASES_VALUE`, `DATE`, `ACCOUNT_ID`) VALUES
(25, 'fdsfsdfsdf', 2000, 1500, 0, '2016-01-01', 3),
(26, 'sdfsdfdsf', 5000, 4500, 500, '2016-02-01', 4);

--
-- Triggers `orders`
--
DELIMITER $$
CREATE TRIGGER `ORDERS_ADD_PUR_VALUE` AFTER UPDATE ON `orders` FOR EACH ROW BEGIN
SET @VALUE = NEW.PURCHASES_VALUE - OLD.PURCHASES_VALUE;
UPDATE ACCOUNTS SET PROFITS = PROFITS - @VALUE WHERE ID = NEW.ACCOUNT_ID;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `SALES_ADD_ORDER` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
SET @TOTAL_SALES = NEW.PAID ;
UPDATE ACCOUNTS SET SALES = SALES + @TOTAL_SALES, BALANCE = BALANCE + @TOTAL_SALES, PROFITS = PROFITS + @TOTAL_SALES WHERE ID = NEW.ACCOUNT_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `outgoings`
--

CREATE TABLE `outgoings` (
  `ID` int(11) NOT NULL,
  `DETAILS` varchar(300) NOT NULL,
  `VALUE` double NOT NULL,
  `DATE` date NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `outgoings`
--

INSERT INTO `outgoings` (`ID`, `DETAILS`, `VALUE`, `DATE`, `ACCOUNT_ID`) VALUES
(3, 'sdfsdfsdf', 100, '2016-01-08', 3),
(4, 'sdfsdfsdf', 100, '2016-01-08', 3);

--
-- Triggers `outgoings`
--
DELIMITER $$
CREATE TRIGGER `OUTGOINGS_ADD` AFTER INSERT ON `outgoings` FOR EACH ROW BEGIN
SET @VALUE = NEW.VALUE ;
UPDATE ACCOUNTS SET OUTGOINGS = OUTGOINGS + @VALUE, BALANCE = BALANCE - @VALUE WHERE ID = NEW.ACCOUNT_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `ID` int(11) NOT NULL,
  `ITEM` varchar(300) NOT NULL,
  `AMOUNT` int(11) NOT NULL,
  `PURCHASE_PRICE` double NOT NULL,
  `SELLER` varchar(100) NOT NULL,
  `DATE` date NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`ID`, `ITEM`, `AMOUNT`, `PURCHASE_PRICE`, `SELLER`, `DATE`, `ACCOUNT_ID`) VALUES
(11, 'sfdsfsdf', 1, 1000, 'asdasdasda', '2016-01-05', 3),
(12, 'asdsdadad', 2, 1000, 'adadad', '2016-01-05', 3);

--
-- Triggers `purchases`
--
DELIMITER $$
CREATE TRIGGER `PURCHASES_ADD` AFTER INSERT ON `purchases` FOR EACH ROW BEGIN
SET @VALUE = NEW.PURCHASE_PRICE ;
UPDATE ACCOUNTS SET PURCHASES = PURCHASES + @VALUE WHERE ID = NEW.ACCOUNT_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `ID` int(11) NOT NULL,
  `ITEM_NAME` varchar(300) NOT NULL,
  `AMOUNT` int(11) NOT NULL,
  `PRICE` double NOT NULL,
  `PAID` double NOT NULL,
  `PURCHASES_VALUE` double NOT NULL,
  `ORDER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`ID`, `ITEM_NAME`, `AMOUNT`, `PRICE`, `PAID`, `PURCHASES_VALUE`, `ORDER_ID`) VALUES
(22, 'dfgdfgdfg', 1, 1000, 1000, 0, 25),
(23, 'dfgdfgdfgdfg', 1, 1000, 500, 0, 25),
(24, 'sdfsdfsdf', 1, 3000, 2500, 500, 26),
(25, 'asdasdasds', 1, 2000, 2000, 0, 26);

--
-- Triggers `sales`
--
DELIMITER $$
CREATE TRIGGER `SALES_ADD_PUR_VALUE` AFTER UPDATE ON `sales` FOR EACH ROW BEGIN
SET @VALUE = NEW.PURCHASES_VALUE - OLD.PURCHASES_VALUE;
UPDATE ORDERS SET PURCHASES_VALUE = PURCHASES_VALUE + @VALUE WHERE ID = NEW.ORDER_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `TITLE` varchar(100) NOT NULL,
  `AUTH` varchar(100) NOT NULL,
  `LAST_EDIT` date NOT NULL,
  `LAST_LOGGED` date NOT NULL,
  `SEC_KEY` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `NAME`, `PASSWORD`, `TITLE`, `AUTH`, `LAST_EDIT`, `LAST_LOGGED`, `SEC_KEY`) VALUES
(1, 'bluemax', '123', 'devo', 'DEV', '2016-02-17', '2016-02-25', 0),
(2, 'ayman', '123', 'admin', 'ADMIN', '2016-02-02', '2016-02-16', 31551);

-- --------------------------------------------------------

--
-- Table structure for table `withdrawals`
--

CREATE TABLE `withdrawals` (
  `ID` int(11) NOT NULL,
  `DETAILS` varchar(300) NOT NULL,
  `VALUE` double NOT NULL,
  `DATE` date NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `withdrawals`
--

INSERT INTO `withdrawals` (`ID`, `DETAILS`, `VALUE`, `DATE`, `ACCOUNT_ID`) VALUES
(4, 'dasdasdasd', 300, '2016-01-06', 3),
(5, 'sdfsdfsdf', 500, '2016-02-22', 4);

--
-- Triggers `withdrawals`
--
DELIMITER $$
CREATE TRIGGER `WITHDRAWALS_ADD` AFTER INSERT ON `withdrawals` FOR EACH ROW BEGIN
SET @VALUE = NEW.VALUE ;
UPDATE ACCOUNTS SET WITHDRAWALS = WITHDRAWALS + @VALUE, BALANCE = BALANCE - @VALUE WHERE ID = NEW.ACCOUNT_ID;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `debts`
--
ALTER TABLE `debts`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ORDER_ID` (`ORDER_ID`);

--
-- Indexes for table `incomes`
--
ALTER TABLE `incomes`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ACCOUNT_ID` (`ACCOUNT_ID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ACCOUNT_ID` (`ACCOUNT_ID`);

--
-- Indexes for table `outgoings`
--
ALTER TABLE `outgoings`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ACCOUNT_ID` (`ACCOUNT_ID`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ACCOUNT_ID` (`ACCOUNT_ID`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ORDER_ID` (`ORDER_ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `withdrawals`
--
ALTER TABLE `withdrawals`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ACCOUNT_ID` (`ACCOUNT_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `debts`
--
ALTER TABLE `debts`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `incomes`
--
ALTER TABLE `incomes`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `outgoings`
--
ALTER TABLE `outgoings`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `withdrawals`
--
ALTER TABLE `withdrawals`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `debts`
--
ALTER TABLE `debts`
  ADD CONSTRAINT `debts_ibfk_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `orders` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `incomes`
--
ALTER TABLE `incomes`
  ADD CONSTRAINT `incomes_ibfk_1` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `outgoings`
--
ALTER TABLE `outgoings`
  ADD CONSTRAINT `outgoings_ibfk_1` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `purchases`
--
ALTER TABLE `purchases`
  ADD CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `orders` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `withdrawals`
--
ALTER TABLE `withdrawals`
  ADD CONSTRAINT `withdrawals_ibfk_1` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `accounts` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
