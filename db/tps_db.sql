-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 21, 2016 at 09:54 PM
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
(1, 2500, 100, 210, 700, 500, 3300, 1810, 100, '2016-02-01'),
(2, 1000, 0, 0, 0, 200, 1000, 800, 0, '2016-03-01');

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
(1, 'kljkljkljkl', 50, '2016-02-21', 0, 1),
(2, 'kfkdjkhfg', 10, '2016-02-21', 1, 1);

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
  `DATE` date NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`ID`, `CUSTOMER`, `PRICE`, `PAID`, `DATE`, `ACCOUNT_ID`) VALUES
(4, 'DASDSD', 100, 100, '2016-02-03', 1),
(21, 'kljkljkljkljkl', 1000, 1000, '2016-02-21', 1),
(22, 'محمود على', 1000, 1000, '2016-02-23', 1),
(23, 'adjkakldj', 500, 500, '2016-02-21', 1),
(24, 'asdasdasd', 1000, 1000, '2016-03-06', 2);

--
-- Triggers `orders`
--
DELIMITER $$
CREATE TRIGGER `SALES_ADD_ORDER` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
SET @TOTAL_SALES = NEW.PAID ;
UPDATE ACCOUNTS SET SALES = SALES + @TOTAL_SALES, BALANCE = BALANCE + @TOTAL_SALES WHERE ID = NEW.ACCOUNT_ID;
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
(1, 'asadasdasd', 300, '2016-02-21', 1),
(2, 'adsdasdasd', 200, '2016-02-21', 1);

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
(1, 'AAAAAAA', 1, 200, 'AAAAAAA', '2016-02-21', 1),
(2, 'sdfdfsdfsdf', 1, 200, 'dsfsdfsdf', '2016-02-21', 1),
(3, 'qqqqqqqq', 1, 300, 'qqqqqqqqqq', '2016-02-21', 1),
(4, 'assssssssss', 1, 500, 'asasas', '2016-02-21', 1),
(5, 'adadasdasd', 1, 300, 'asdasdad', '2016-02-21', 1),
(6, 'asdasdas', 1, 500, 'sdasdasd', '2016-02-21', 1),
(7, 'adasdasd', 1, 100, 'asdasd', '2016-02-21', 1),
(8, 'asdasd', 1, 900, 'asdasd', '2016-02-21', 1),
(9, 'vdfdsfdsfsd', 1, 100, 'sdfsdfsdf', '2016-03-07', 2),
(10, 'asdasd', 1, 900, 'asdasd', '2016-03-07', 2);

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
(16, 'kklskdlsdk', 1, 1000, 1000, 0, 21),
(17, 'يشسمينكمشسنيكمن', 1, 500, 500, 0, 22),
(18, 'ععععععععع', 1, 500, 500, 0, 22),
(19, 'akd;lakdka;lk', 1, 500, 500, 0, 23),
(20, 'asdasdasd', 1, 500, 500, 0, 24),
(21, 'ddfsdfsdf', 1, 500, 500, 0, 24);

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
(2, 'ayman', '123', 'admin', 'ADMIN', '2016-02-02', '2016-02-16', 9656);

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
(1, 'asdasdasd', 250, '2016-02-21', 1),
(2, 'asdasdasd', 100, '2016-03-15', 2),
(3, 'asdasd', 100, '2016-03-15', 2);

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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `debts`
--
ALTER TABLE `debts`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `incomes`
--
ALTER TABLE `incomes`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `outgoings`
--
ALTER TABLE `outgoings`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `withdrawals`
--
ALTER TABLE `withdrawals`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
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
