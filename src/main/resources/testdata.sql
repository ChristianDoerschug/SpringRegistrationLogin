Insert INTO user(id, email, username, passwordHash, role, activated, firstName, lastName, birthday, country, created, edited) VALUES
(1, 'ingo@fh-muenster.de', 'Ingo', '$2a$10$CwryjPaRyWedMdsDGDZ86e3s2EjXAxwVkAssoiOX7zjgCvIyVF8KS', 1, false, 'Ingo', 'Test', '2009-03-03', 'Germany', '2021-06-23', '2021-06-24'), --password: Password123
(2, 'peter@fh-muenster.de', 'Peter', '$2a$10$CwryjPaRyWedMdsDGDZ86e3s2EjXAxwVkAssoiOX7zjgCvIyVF8KS', 1, false, 'Peter', 'Test', '2014-03-03', 'France', '2021-06-22', '2021-06-24'), --password: Password123
(3, 'hans@fh-muenster.de', 'Hans', '$2a$10$CwryjPaRyWedMdsDGDZ86e3s2EjXAxwVkAssoiOX7zjgCvIyVF8KS', 1, true, 'Hans', 'Test', '2005-03-03', 'Belgium', '2021-05-24', '2021-06-24'), --password: Password123
(4, 'alex@fh-muenster.de', 'Alex', '$2a$10$CwryjPaRyWedMdsDGDZ86e3s2EjXAxwVkAssoiOX7zjgCvIyVF8KS', 1, true, 'Alex', 'Test', '1990-03-03', 'Germany', '2021-06-24', '2021-06-24'), --password: Password123
(5, 'admin@fh-muenster.de', 'AdminTest', '$2a$10$LNXXJMsiz8G9I8g14VHwz.dci0P0Bm0nXkoGXRDfpu.fTAgz4OATu', 0, true, 'Admin', 'Test', '1990-03-03', 'Germany', '2020-06-24', '2020-06-24'), --password: !Admin123
(6, 'Mod@fh-muenster.de', 'Mod', '$2a$10$LNXXJMsiz8G9I8g14VHwz.dci0P0Bm0nXkoGXRDfpu.fTAgz4OATu', 2, true, 'Mod', 'Test', '1990-03-03', 'Germany', '2020-12-24', '2020-12-24'); --password: !Admin123