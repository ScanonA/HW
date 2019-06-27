-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 18, 2019 at 04:58 AM
-- Server version: 8.0.16
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mysocial`
--

-- --------------------------------------------------------

--
-- Table structure for table `ad`
--

CREATE TABLE `ad` (
  `Link` char(255) NOT NULL,
  `Caption` varchar(140) DEFAULT NULL,
  `Num_views` int(11) DEFAULT NULL,
  `Num_clicks` int(11) DEFAULT NULL,
  `Email_address` char(255) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `Email_address` char(255) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL,
  `IP_Address` char(45) NOT NULL,
  `Employee_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`Email_address`, `Password`, `Name`, `ID`, `IP_Address`, `Employee_ID`) VALUES
('important@outlook.com', 'Hard2GuessPassword', 'BigBadAdmin', 6, '333', 1);

-- --------------------------------------------------------

--
-- Table structure for table `advertiser`
--

CREATE TABLE `advertiser` (
  `Email_address` char(255) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL,
  `IP_Address` char(45) NOT NULL,
  `Company_name` char(48) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `advertiser`
--

INSERT INTO `advertiser` (`Email_address`, `Password`, `Name`, `ID`, `IP_Address`, `Company_name`) VALUES
('bigCompany@company.com', 'M0ney', 'Carl', 3, '222', 'Big Company'),
('smallCorp@company.com', 'adPassword', 'TheLegend28', 5, '333', 'Small Company');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `CText` varchar(140) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL,
  `Link` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`CText`, `Name`, `ID`, `Link`) VALUES
('haha', 'anon', 403, 'image.com/image111');

-- --------------------------------------------------------

--
-- Table structure for table `contains`
--

CREATE TABLE `contains` (
  `Name` char(16) NOT NULL,
  `Link` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `contains`
--

INSERT INTO `contains` (`Name`, `Link`) VALUES
('gaming', 'https://www.youtube.com/watch?v=1JsXDMnmvww');

-- --------------------------------------------------------

--
-- Table structure for table `follow`
--

CREATE TABLE `follow` (
  `Email_address1` char(255) NOT NULL,
  `Email_address2` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `follow`
--

INSERT INTO `follow` (`Email_address1`, `Email_address2`) VALUES
('aidanbjelke@gmail.com', 'aidanbjelke@gmail.com'),
('aidanbjelke@gmail.com', 'bubbleBlower@hotmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `logged_in`
--

CREATE TABLE `logged_in` (
  `Email_address` char(255) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL,
  `IP_Address` char(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `logged_in`
--

INSERT INTO `logged_in` (`Email_address`, `Password`, `Name`, `ID`, `IP_Address`) VALUES
('aidanbjelke@gmail.com', 'usual', 'bungle', 461, '::1'),
('bigCompany@company.com', 'M0ney', 'Carl', 3, '222'),
('bubbleBlower@hotmail.com', 'bubbles', 'Steven', 2, '510'),
('fireman64@gmail.com', 'noFires', 'Carl', 1, '111'),
('important@outlook.com', 'Hard2GuessPassword', 'BigBadAdmin', 6, '333'),
('smallCorp@company.com', 'adPassword', 'TheLegend28', 5, '333'),
('someOne@gmail.com', 'noTwo', 'Aidan', 0, '314'),
('someTwo@gmail.com', 'password', 'TheLegend27', 4, '333');

-- --------------------------------------------------------

--
-- Table structure for table `moderates`
--

CREATE TABLE `moderates` (
  `Name` char(16) NOT NULL,
  `Email_address` char(255) NOT NULL,
  `Email_address2` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `picture`
--

CREATE TABLE `picture` (
  `Link` char(255) NOT NULL,
  `Caption` varchar(140) DEFAULT NULL,
  `Resolution` char(16) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `picture`
--

INSERT INTO `picture` (`Link`, `Caption`, `Resolution`, `Name`, `ID`) VALUES
('image.com/image111', 'Haha look at this cat', '1200x1420', 'BigBadAdmin', 6),
('image.com/image555', 'Look at this bubble', '2080x1440', 'Steven', 2),
('image.com/image658', 'These fires are out of control', '1000x1000', 'Carl', 1),
('image.com/image789', 'This was a great victory', '1080x720', 'TheLegend27', 4);

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `Link` char(255) NOT NULL,
  `Caption` varchar(140) DEFAULT NULL,
  `Email_address` char(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`Link`, `Caption`, `Email_address`) VALUES
('https://www.youtube.com/watch?v=1JsXDMnmvww', 'destroy all humans 2 gameplay', 'aidanbjelke@gmail.com'),
('https://www.youtube.com/watch?v=uuX_2MDaEzY', 'excited for smm2', NULL),
('image.com/image111', 'Haha look at this cat', 'important@outlook.com'),
('image.com/image555', 'Look at this bubble', 'bubbleBlower@hotmail.com'),
('image.com/image658', 'These fires are out of control', 'fireman64@gmail.com'),
('image.com/image789', 'This was a great victory', 'someTwo@gmail.com'),
('page.text1', 'Look at these words.', 'someOne@gmail.com'),
('page.text2', 'Who need images when you have text', 'someTwo@gmail.com'),
('page.text3', 'Bubbles.', 'bubbleBlower@hotmail.com'),
('page.text4', 'This is my second post', 'someOne@gmail.com'),
('page.text5', 'Don\'t do anything I wouldn\'t', 'important@outlook.com'),
('youtube.com/watch/7fhg7g6', 'So many BUBBLES', 'someTwo@gmail.com'),
('youtube.com/watch/asfdsaf', 'Spoilers for Avengers Endgame', 'fireman64@gmail.com'),
('youtube.com/watch/d4was564d', 'More high quality bubbless', 'bubbleBlower@hotmail.com'),
('youtube.com/watch/ds45ad46', '', 'important@outlook.com'),
('youtube.com/watch/r41ew354r', 'Buy gold!!', 'fireman64@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `Link` char(255) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `repost`
--

CREATE TABLE `repost` (
  `Link` char(255) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribes_to`
--

CREATE TABLE `subscribes_to` (
  `Name` char(16) NOT NULL,
  `Email_address` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `Link` char(255) NOT NULL,
  `Tagtext` char(24) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `thread`
--

CREATE TABLE `thread` (
  `Name` char(16) NOT NULL,
  `Email_address` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `thread`
--

INSERT INTO `thread` (`Name`, `Email_address`) VALUES
('gaming', 'aidanbjelke@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL,
  `IP_Address` char(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Name`, `ID`, `IP_Address`) VALUES
('Aidan', 0, '314'),
('anon', 403, '::1'),
('anon', 581, '::1'),
('BigBadAdmin', 6, '333'),
('bungle', 461, '::1'),
('Carl', 1, '111'),
('Carl', 3, '222'),
('Steven', 2, '510'),
('TheLegend27', 4, '333'),
('TheLegend28', 5, '333');

-- --------------------------------------------------------

--
-- Table structure for table `video`
--

CREATE TABLE `video` (
  `Link` char(255) NOT NULL,
  `Caption` varchar(140) DEFAULT NULL,
  `Resolution` char(16) NOT NULL,
  `Length` int(11) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vote`
--

CREATE TABLE `vote` (
  `Link` char(255) NOT NULL,
  `Name` char(16) NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `vote`
--

INSERT INTO `vote` (`Link`, `Name`, `ID`) VALUES
('image.com/image658', 'anon', 403),
('image.com/image111', 'anon', 581);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ad`
--
ALTER TABLE `ad`
  ADD PRIMARY KEY (`Link`),
  ADD KEY `Email_address` (`Email_address`),
  ADD KEY `Name` (`Name`,`ID`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`Email_address`),
  ADD UNIQUE KEY `Name` (`Name`,`ID`,`Employee_ID`);

--
-- Indexes for table `advertiser`
--
ALTER TABLE `advertiser`
  ADD PRIMARY KEY (`Email_address`),
  ADD UNIQUE KEY `Name` (`Name`,`ID`,`Company_name`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`Name`,`ID`,`CText`,`Link`) USING BTREE,
  ADD KEY `Link` (`Link`) USING BTREE;

--
-- Indexes for table `contains`
--
ALTER TABLE `contains`
  ADD PRIMARY KEY (`Name`,`Link`),
  ADD KEY `Link` (`Link`);

--
-- Indexes for table `follow`
--
ALTER TABLE `follow`
  ADD PRIMARY KEY (`Email_address1`,`Email_address2`),
  ADD KEY `Email_address2` (`Email_address2`);

--
-- Indexes for table `logged_in`
--
ALTER TABLE `logged_in`
  ADD PRIMARY KEY (`Email_address`),
  ADD UNIQUE KEY `Name` (`Name`,`ID`);

--
-- Indexes for table `moderates`
--
ALTER TABLE `moderates`
  ADD PRIMARY KEY (`Name`,`Email_address`,`Email_address2`),
  ADD KEY `Email_address` (`Email_address`),
  ADD KEY `Email_address2` (`Email_address2`);

--
-- Indexes for table `picture`
--
ALTER TABLE `picture`
  ADD PRIMARY KEY (`Link`),
  ADD KEY `Name` (`Name`,`ID`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`Link`),
  ADD KEY `Email_address` (`Email_address`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`Link`,`Name`,`ID`),
  ADD KEY `Name` (`Name`,`ID`);

--
-- Indexes for table `repost`
--
ALTER TABLE `repost`
  ADD PRIMARY KEY (`Link`,`Name`,`ID`),
  ADD KEY `Name` (`Name`,`ID`);

--
-- Indexes for table `subscribes_to`
--
ALTER TABLE `subscribes_to`
  ADD PRIMARY KEY (`Name`,`Email_address`),
  ADD KEY `Email_address` (`Email_address`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`Link`,`Tagtext`);

--
-- Indexes for table `thread`
--
ALTER TABLE `thread`
  ADD PRIMARY KEY (`Name`),
  ADD KEY `Email_address` (`Email_address`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Name`,`ID`);

--
-- Indexes for table `video`
--
ALTER TABLE `video`
  ADD PRIMARY KEY (`Link`),
  ADD KEY `Name` (`Name`,`ID`);

--
-- Indexes for table `vote`
--
ALTER TABLE `vote`
  ADD PRIMARY KEY (`Link`,`Name`,`ID`),
  ADD KEY `Name` (`Name`,`ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ad`
--
ALTER TABLE `ad`
  ADD CONSTRAINT `ad_ibfk_1` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ad_ibfk_2` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ad_ibfk_3` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `admin_ibfk_2` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `advertiser`
--
ALTER TABLE `advertiser`
  ADD CONSTRAINT `advertiser_ibfk_1` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `advertiser_ibfk_2` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `contains`
--
ALTER TABLE `contains`
  ADD CONSTRAINT `contains_ibfk_1` FOREIGN KEY (`Name`) REFERENCES `thread` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `contains_ibfk_2` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `follow`
--
ALTER TABLE `follow`
  ADD CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`Email_address1`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`Email_address2`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `logged_in`
--
ALTER TABLE `logged_in`
  ADD CONSTRAINT `logged_in_ibfk_1` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `moderates`
--
ALTER TABLE `moderates`
  ADD CONSTRAINT `moderates_ibfk_1` FOREIGN KEY (`Name`) REFERENCES `thread` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `moderates_ibfk_2` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `moderates_ibfk_3` FOREIGN KEY (`Email_address2`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `picture`
--
ALTER TABLE `picture`
  ADD CONSTRAINT `picture_ibfk_1` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `picture_ibfk_2` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `report_ibfk_2` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `repost`
--
ALTER TABLE `repost`
  ADD CONSTRAINT `repost_ibfk_1` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `repost_ibfk_2` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `subscribes_to`
--
ALTER TABLE `subscribes_to`
  ADD CONSTRAINT `subscribes_to_ibfk_1` FOREIGN KEY (`Name`) REFERENCES `thread` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `subscribes_to_ibfk_2` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tag`
--
ALTER TABLE `tag`
  ADD CONSTRAINT `tag_ibfk_1` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `thread`
--
ALTER TABLE `thread`
  ADD CONSTRAINT `thread_ibfk_1` FOREIGN KEY (`Email_address`) REFERENCES `logged_in` (`Email_address`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `video`
--
ALTER TABLE `video`
  ADD CONSTRAINT `video_ibfk_1` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `video_ibfk_2` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vote`
--
ALTER TABLE `vote`
  ADD CONSTRAINT `vote_ibfk_1` FOREIGN KEY (`Name`,`ID`) REFERENCES `user` (`Name`, `ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vote_ibfk_2` FOREIGN KEY (`Link`) REFERENCES `post` (`Link`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
