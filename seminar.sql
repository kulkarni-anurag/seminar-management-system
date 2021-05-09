-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2021 at 08:48 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `seminar`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `eid` int(11) NOT NULL,
  `ename` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `evenue` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `edate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `eduration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eid`, `ename`, `evenue`, `edate`, `eduration`) VALUES
(1, 'Bootcamp', 'Online', '2021-07-27 18:30:00', 2),
(2, 'CPP BootCamp', 'Online', '2021-03-12 18:30:00', 3),
(3, 'Cloud Fest', 'Auditorium', '2020-06-16 18:30:00', 6);

-- --------------------------------------------------------

--
-- Table structure for table `guest`
--

CREATE TABLE `guest` (
  `gid` int(11) NOT NULL,
  `gname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `ggender` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `gexpert` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `gcompany` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `event` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `guest`
--

INSERT INTO `guest` (`gid`, `gname`, `ggender`, `gexpert`, `gcompany`, `event`) VALUES
(4, 'Anurag Kulkarni', 'Male', 'Software Development', 'Google', 2);

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `rid` int(11) NOT NULL,
  `grno` int(11) NOT NULL,
  `event` int(11) NOT NULL,
  `attendance` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`rid`, `grno`, `event`, `attendance`) VALUES
(1, 11910287, 3, 1),
(2, 11920201, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `gr_no` int(11) NOT NULL,
  `sname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `sbranch` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `syear` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`gr_no`, `sname`, `sbranch`, `syear`) VALUES
(11910287, 'Anurag Kulkarni', 'Instrumentation', 'SY'),
(11920201, 'Virat Kohli', 'Computer', 'FY');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`gid`);

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`rid`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`gr_no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `eid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `guest`
--
ALTER TABLE `guest`
  MODIFY `gid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `registration`
--
ALTER TABLE `registration`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
