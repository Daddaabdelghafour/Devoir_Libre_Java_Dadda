-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2024 at 04:17 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `devoirlibre`
--

-- --------------------------------------------------------

--
-- Table structure for table `banque`
--

CREATE TABLE `banque` (
  `id` int(11) NOT NULL,
  `pays` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `banque`
--

INSERT INTO `banque` (`id`, `pays`) VALUES
(1, 'Maroc'),
(2, 'Maroc'),
(3, 'Netherlands'),
(4, 'Nigeria'),
(5, 'France'),
(6, 'France'),
(7, 'France'),
(8, 'France'),
(9, 'France'),
(10, 'France'),
(11, 'France'),
(12, 'France'),
(13, 'France'),
(14, 'France'),
(15, 'Maroc'),
(16, 'Maroc'),
(17, 'France'),
(18, 'Maroc'),
(19, 'Maroc'),
(20, 'France'),
(21, 'Maroc'),
(22, 'Maroc'),
(23, 'France'),
(24, 'Maroc'),
(25, 'Maroc'),
(26, 'France'),
(27, 'Maroc'),
(28, 'Maroc'),
(29, 'Maroc');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `NumClient` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`NumClient`, `Nom`, `prenom`, `adresse`, `phone`, `email`) VALUES
(1, 'Doe', NULL, '123 Main Street', '555-1234', 'john.doe@example.com'),
(2, 'Abdelghafour', NULL, '123 Casablanca', '555-1234', 'abdelghafourDadda@gmail.com'),
(6, 'Saad', 'Amrani', '123 Iziki, Marrakech', '06688745', 'saada@gmail.com'),
(7, 'Hiba', 'Dadda', '123 Sokoma, Marrakech', '0668874', 'hibad@gmail.com'),
(8, 'mohammed', 'Saadi', '123 massira, Marrakech', '0688741', 'mohammeds@gmail.com'),
(33, 'rihab', 'Arr', '123 Iziki, Marrakech', '06688745', 'rihab@gmail.com'),
(34, 'rayan', 'ds', '123 Sokoma, Marrakech', '0668874', 'rayanf@gmail.com'),
(35, 'abdou', 'Saadi', '123 massira, Marrakech', '0688741', 'abousa@gmail.com'),
(36, 'salman', 'far', '123 Iziki, Marrakech', '06688745', 'salmanf@gmail.com'),
(37, 'louis', 'cxvc', '123 Sokoma, Marrakech', '0668874', 'louiss@gmail.com'),
(38, 'soulayman', 'moucho', '123 massira, Marrakech', '0688741', 'soulv@gmail.com'),
(39, 'anouar', 'farhat', '123 Iziki, Marrakech', '06688745', 'anouarf@gmail.com'),
(40, 'Simo', 'Sedraty', '123 Sokoma, Marrakech', '0668874', 'simosed@gmail.com'),
(41, 'rawya', 'hamdi', '123 Iziki, Marrakech', '06688745', 'rawyah@gmail.com'),
(42, 'Sanae', 'himani', '123 Sokoma, Marrakech', '0668874', 'sanaef@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `compte`
--

CREATE TABLE `compte` (
  `numcompte` int(11) NOT NULL,
  `dateCreation` date DEFAULT NULL,
  `dateUpdate` date DEFAULT NULL,
  `Devise` varchar(50) DEFAULT NULL,
  `numclient` int(11) DEFAULT NULL,
  `banque_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `compte`
--

INSERT INTO `compte` (`numcompte`, `dateCreation`, `dateUpdate`, `Devise`, `numclient`, `banque_id`) VALUES
(1, '2024-01-01', '2024-11-01', 'USD', 1, 1),
(17, '2024-01-01', '2024-01-01', 'EUR', 33, 20),
(18, '2024-01-01', '2024-01-01', 'USD', 34, 21),
(19, '2024-01-01', '2024-01-01', 'USD', 35, 22),
(20, '2024-01-01', '2024-01-01', 'EUR', 36, 23),
(21, '2024-01-01', '2024-01-01', 'USD', 37, 24),
(22, '2024-01-01', '2024-01-01', 'USD', 38, 25),
(23, '2024-01-01', '2024-01-01', 'EUR', 39, 26),
(24, '2024-01-01', '2024-01-01', 'USD', 40, 27),
(25, '2024-01-01', '2024-01-01', 'EUR', 41, 28),
(26, '2024-01-01', '2024-01-01', 'USD', 42, 29);

-- --------------------------------------------------------

--
-- Table structure for table `compte_transaction`
--

CREATE TABLE `compte_transaction` (
  `compte_numcompte` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `compte_transaction`
--

INSERT INTO `compte_transaction` (`compte_numcompte`, `transaction_id`) VALUES
(20, 3),
(21, 3),
(22, 3),
(23, 4),
(24, 4),
(25, 5),
(26, 5);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `reference` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `type`, `timestamp`, `reference`) VALUES
(1, 'VirMulta', '2024-11-07 13:57:33', 'REF-aa17452e-eaef-4a5e-9269-a0008c8fec1c'),
(2, 'VirMulta', '2024-11-07 14:02:01', 'REF-4b6311fb-011a-41ef-a303-059618ca6722'),
(3, 'VirMulta', '2024-11-07 14:23:11', 'REF-ae9be3ac-0262-459a-9a8e-89e2940a4e1c'),
(4, 'Virchac', '2024-11-07 14:29:17', 'REF-cf894ab6-9299-438d-a9a1-1c0a985f780a'),
(5, 'Virest', '2024-11-07 14:35:48', 'REF-0d2ed767-38e1-4abd-a24a-4e070cd7c697');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banque`
--
ALTER TABLE `banque`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`NumClient`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`numcompte`),
  ADD KEY `numclient` (`numclient`),
  ADD KEY `banque_id` (`banque_id`);

--
-- Indexes for table `compte_transaction`
--
ALTER TABLE `compte_transaction`
  ADD PRIMARY KEY (`compte_numcompte`,`transaction_id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `reference` (`reference`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banque`
--
ALTER TABLE `banque`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `NumClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `compte`
--
ALTER TABLE `compte`
  MODIFY `numcompte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `compte_ibfk_1` FOREIGN KEY (`numclient`) REFERENCES `client` (`NumClient`),
  ADD CONSTRAINT `compte_ibfk_2` FOREIGN KEY (`banque_id`) REFERENCES `banque` (`id`);

--
-- Constraints for table `compte_transaction`
--
ALTER TABLE `compte_transaction`
  ADD CONSTRAINT `compte_transaction_ibfk_1` FOREIGN KEY (`compte_numcompte`) REFERENCES `compte` (`numcompte`),
  ADD CONSTRAINT `compte_transaction_ibfk_2` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
