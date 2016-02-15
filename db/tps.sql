-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 15, 2016 at 05:16 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 7.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tps`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `ID` bigint(20) NOT NULL,
  `TotalSales` double NOT NULL,
  `TotalIncomes` double NOT NULL,
  `TotalOutgoings` double NOT NULL,
  `TotalWithdrawals` double NOT NULL,
  `TotalPurchases` double NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`ID`, `TotalSales`, `TotalIncomes`, `TotalOutgoings`, `TotalWithdrawals`, `TotalPurchases`, `Date`) VALUES
(1, 1100, 600, 0, 0, 0, '2016-02-01');

-- --------------------------------------------------------

--
-- Table structure for table `incomes`
--

CREATE TABLE `incomes` (
  `ID` bigint(20) NOT NULL,
  `Details` varchar(300) NOT NULL,
  `Value` double NOT NULL,
  `IsDebt` tinyint(1) NOT NULL DEFAULT '0',
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `incomes`
--

INSERT INTO `incomes` (`ID`, `Details`, `Value`, `IsDebt`, `Date`) VALUES
(1, 'باقى حساب عبد السلام ', 500, 1, '2016-02-10'),
(2, 'hhhhhhhh', 100, 0, '2016-02-16'),
(3, 'hhhhhhhh', 100, 1, '2016-02-16'),
(4, 'ppppppppp', 100, 0, '2016-02-15'),
(5, 'ppppppppp', 100, 1, '2016-02-15'),
(6, 'wwwwwwww', 100, 0, '2016-02-15'),
(7, 'wwwwwwww', 100, 1, '2016-02-15'),
(8, 'wwwwwwww', 100, 0, '2016-02-15');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `ID` bigint(20) NOT NULL,
  `Customer` varchar(100) NOT NULL,
  `Discount` double NOT NULL,
  `TotalPrice` double NOT NULL,
  `Paid` double NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`ID`, `Customer`, `Discount`, `TotalPrice`, `Paid`, `Date`) VALUES
(1, 'ahmed ali', 100, 1000, 800, '2016-02-05'),
(11, 'محمود عبد العال', 200, 5500, 5500, '2016-02-06'),
(12, 'محمود عبد العال', 200, 5500, 5500, '2016-02-06'),
(13, 'محمود عبد العال', 200, 5500, 5500, '2016-02-06'),
(14, '', 0, 0, 0, '0000-00-00'),
(15, 'محمود على', 0, 300, 300, '2016-02-07'),
(16, 'dfsfsdf', 0, 121, 1212, '2016-02-07'),
(17, '', 0, 0, 0, '0000-00-00'),
(18, '', 0, 0, 0, '0000-00-00'),
(19, '', 0, 0, 0, '0000-00-00'),
(20, 'asdasdasd', 0, 100, 100, '2016-02-07'),
(21, '', 0, 0, 0, '0000-00-00'),
(22, '', 0, 0, 0, '0000-00-00'),
(23, 'oooooooooo', 0, 100, 100, '2016-02-07'),
(24, 'pppppppp', 0, 100, 100, '2016-02-07'),
(25, 'uuuuuuuuu', 0, 100, 100, '2016-02-07'),
(26, 'Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹', 0, 100, 100, '2016-02-07'),
(27, 'فففففففففففف', 0, 100, 100, '2016-02-07'),
(28, 'محمود على', 0, 1500, 1500, '2016-02-07'),
(29, 'محمود', 0, 100, 100, '2016-02-07'),
(30, 'aasdasd', 0, 10000, 10000, '2016-02-07'),
(31, 'احمد على', 0, 1000, 1000, '2016-02-01'),
(32, 'asdldkla;sdkl', 0, 100, 100, '2016-02-01'),
(33, 'oooooo', 0, 1, 1, '2016-02-01'),
(34, 'wewewe', 0, 1, 1, '2016-02-01'),
(35, 'qqqqqq', 0, 1, 1, '2016-02-01'),
(36, 'eeeeeee', 0, 1, 1, '2016-02-01'),
(37, 'rrrrrrr', 0, 1, 1, '2016-02-01'),
(38, 'yyyyyy', 0, 1, 1, '2016-02-01'),
(39, 'uuuuuuu', 0, 99, 99, '2016-02-01'),
(40, 'eeeeeeeee', 0, 1000, 1000, '2016-02-15');

-- --------------------------------------------------------

--
-- Table structure for table `outgoings`
--

CREATE TABLE `outgoings` (
  `ID` bigint(20) NOT NULL,
  `Details` varchar(300) NOT NULL,
  `Value` double NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `outgoings`
--

INSERT INTO `outgoings` (`ID`, `Details`, `Value`, `Date`) VALUES
(1, 'راتب شهر 10 ', 2000, '2016-01-24'),
(2, 'qqqqqqqqqqqqqq', 1000, '2016-01-11'),
(3, 'eeeeeeeeeee', 1000, '2016-01-04'),
(4, 'mmmmmmmmmm', 1000, '2016-01-04'),
(5, 'cccccccc', 1000, '2016-01-04'),
(6, 'محمود فتحى محمد', 5, '2016-01-13'),
(7, 'محمود فتحى على', 10, '2016-01-13');

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `ID` bigint(20) NOT NULL,
  `Item` varchar(200) NOT NULL,
  `Amount` int(11) NOT NULL,
  `PurchasePrice` double NOT NULL,
  `Seller` varchar(100) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`ID`, `Item`, `Amount`, `PurchasePrice`, `Seller`, `Date`) VALUES
(1, 'تربو ك 10', 2, 5000, 'محمود قرنى', '2016-02-10');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `ID` bigint(20) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Amount` int(11) NOT NULL,
  `Price` double NOT NULL,
  `Discount` double NOT NULL,
  `SoldPrice` double NOT NULL,
  `Paid` double NOT NULL,
  `PurchasesValue` double NOT NULL,
  `OrderID` bigint(20) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`ID`, `Name`, `Amount`, `Price`, `Discount`, `SoldPrice`, `Paid`, `PurchasesValue`, `OrderID`, `Date`) VALUES
(1, 'turbo 200', 1, 1000, 100, 900, 900, 500, 1, '2016-02-05'),
(8, 'تربو ك 10', 1, 4100, 100, 4000, 4000, 0, 11, '2016-02-06'),
(9, 'كومبروسر ك 10', 1, 1600, 100, 1500, 1500, 0, 11, '2016-02-06'),
(10, 'تربو ك 10', 1, 4100, 100, 4000, 4000, 0, 12, '2016-02-06'),
(11, 'كومبروسر ك 10', 1, 1600, 100, 1500, 1500, 0, 12, '2016-02-06'),
(12, 'تربو ك 10', 1, 4100, 100, 4000, 4000, 0, 13, '2016-02-06'),
(13, 'كومبروسر ك 10', 1, 1600, 100, 1500, 1500, 0, 13, '2016-02-06'),
(14, 'خرطوم 100 سم', 1, 100, 0, 100, 100, 0, 15, '2016-02-07'),
(15, 'خزان كبير ', 2, 200, 0, 200, 200, 0, 15, '2016-02-07'),
(16, 'dsadasdasd', 1, 1212, 0, 121, 1212, 0, 16, '2016-02-07'),
(17, 'wwwwwwwwww', 1, 100, 0, 100, 100, 0, 20, '2016-02-07'),
(18, 'ooooooooooooooo', 1, 100, 0, 100, 100, 0, 23, '2016-02-07'),
(19, 'ppppppppppp', 1, 100, 0, 100, 100, 0, 24, '2016-02-07'),
(20, 'uuuuuuuuu', 1, 100, 0, 100, 100, 0, 25, '2016-02-07'),
(21, 'Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹Ø¹', 1, 100, 0, 100, 100, 0, 26, '2016-02-07'),
(22, 'فففففففففففففففف', 1, 100, 0, 100, 100, 0, 27, '2016-02-07'),
(23, 'شريحة مكينة ك 10', 1, 1500, 0, 1500, 1500, 0, 28, '2016-02-07'),
(24, 'محمود', 1, 100, 0, 100, 100, 0, 29, '2016-02-07'),
(25, 'lasdalsd', 1, 10000, 0, 10000, 10000, 0, 30, '2016-02-07'),
(26, 'محمول', 1, 1000, 0, 1000, 1000, 0, 31, '2016-02-01'),
(27, 'dlsakd;lkl', 1, 100, 0, 100, 100, 0, 32, '2016-02-01'),
(28, 'lklklklk', 1, 1, 0, 1, 1, 0, 33, '2016-02-01'),
(29, 'wwwwww', 1, 1, 0, 1, 1, 0, 34, '2016-02-01'),
(30, 'qqqqqqqqqqq', 1, 1, 0, 1, 1, 0, 35, '2016-02-01'),
(31, 'eeeeeeeee', 1, 1, 0, 1, 1, 0, 36, '2016-02-01'),
(32, 'rrrrrrrr', 1, 1, 0, 1, 1, 0, 37, '2016-02-01'),
(33, 'yyyyyyyyy', 1, 1, 0, 1, 1, 0, 38, '2016-02-01'),
(34, 'uuuuuuuu', 1, 99, 0, 99, 99, 0, 39, '2016-02-01'),
(35, 'eeeeeeeeeeee', 1, 1000, 0, 1000, 1000, 0, 40, '2016-02-15');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` bigint(20) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Auth` varchar(50) NOT NULL,
  `LastEdit` date NOT NULL,
  `LastLogged` date NOT NULL,
  `SecKey` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Name`, `Password`, `Title`, `Auth`, `LastEdit`, `LastLogged`, `SecKey`) VALUES
(1, 'bluemax', '234597458', 'devo', 'DEV', '2016-01-08', '2016-01-31', 0),
(2, 'ayman', '123', 'Admin', 'ADMIN', '2016-01-08', '2016-01-31', 17897);

-- --------------------------------------------------------

--
-- Table structure for table `withdrawals`
--

CREATE TABLE `withdrawals` (
  `ID` bigint(20) NOT NULL,
  `Details` varchar(300) NOT NULL,
  `Value` double NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `withdrawals`
--

INSERT INTO `withdrawals` (`ID`, `Details`, `Value`, `Date`) VALUES
(1, 'يد ايمن فراج سحب نقدى', 10000, '2016-02-09');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Date` (`Date`);

--
-- Indexes for table `incomes`
--
ALTER TABLE `incomes`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `outgoings`
--
ALTER TABLE `outgoings`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `withdrawals`
--
ALTER TABLE `withdrawals`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `incomes`
--
ALTER TABLE `incomes`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `outgoings`
--
ALTER TABLE `outgoings`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `withdrawals`
--
ALTER TABLE `withdrawals`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
