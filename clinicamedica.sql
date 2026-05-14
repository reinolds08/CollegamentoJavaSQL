-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 14, 2026 at 12:42 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clinicamedica`
--

-- --------------------------------------------------------

--
-- Table structure for table `pazienti`
--

CREATE TABLE `pazienti` (
  `id_persona` int(11) NOT NULL,
  `NomePersona` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pazienti`
--

INSERT INTO `pazienti` (`id_persona`, `NomePersona`) VALUES
(15, 'Davide Rinaldi');

-- --------------------------------------------------------

--
-- Table structure for table `prenotazioni`
--

CREATE TABLE `prenotazioni` (
  `id_visita` int(11) NOT NULL,
  `dataOraVisita` datetime NOT NULL,
  `TipoVisita` varchar(50) NOT NULL,
  `Priorità` tinyint(1) NOT NULL,
  `Paziente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `visite_effettuate`
--

CREATE TABLE `visite_effettuate` (
  `id_record` int(11) NOT NULL,
  `dataOraVisita` datetime NOT NULL,
  `TipoVisita` varchar(100) NOT NULL,
  `Priorità` tinyint(1) NOT NULL,
  `Paziente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `visite_effettuate`
--

INSERT INTO `visite_effettuate` (`id_record`, `dataOraVisita`, `TipoVisita`, `Priorità`, `Paziente`) VALUES
(1, '2026-05-14 08:30:00', 'radiologia', 1, 15);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pazienti`
--
ALTER TABLE `pazienti`
  ADD PRIMARY KEY (`id_persona`);

--
-- Indexes for table `prenotazioni`
--
ALTER TABLE `prenotazioni`
  ADD PRIMARY KEY (`id_visita`),
  ADD KEY `Paziente` (`Paziente`);

--
-- Indexes for table `visite_effettuate`
--
ALTER TABLE `visite_effettuate`
  ADD PRIMARY KEY (`id_record`),
  ADD KEY `Paziente` (`Paziente`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pazienti`
--
ALTER TABLE `pazienti`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `prenotazioni`
--
ALTER TABLE `prenotazioni`
  MODIFY `id_visita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `visite_effettuate`
--
ALTER TABLE `visite_effettuate`
  MODIFY `id_record` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `prenotazioni`
--
ALTER TABLE `prenotazioni`
  ADD CONSTRAINT `prenotazioni_ibfk_1` FOREIGN KEY (`Paziente`) REFERENCES `pazienti` (`id_persona`);

--
-- Constraints for table `visite_effettuate`
--
ALTER TABLE `visite_effettuate`
  ADD CONSTRAINT `visite_effettuate_ibfk_1` FOREIGN KEY (`Paziente`) REFERENCES `pazienti` (`id_persona`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
