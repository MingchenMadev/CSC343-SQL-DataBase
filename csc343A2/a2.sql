-- Add below your SQL statements. 
-- You can create intermediate views (as needed). Remember to drop these views after you have populated the result tables.
-- You can use the "\i a2.sql" command in psql to execute the SQL commands in this file.

-- Query 1 statements



-- Query 2 statements
SELECT country.cid, country.cname
FROM country
EXCEPT
SELECT DISTINCT country.cid, country.cname
FROM country
NATURAL JOIN
oceanAccess
ORDER BY cname ASC;


-- Query 3 statements

CREATE VIEW landlocked_country AS
SELECT country.cid, country.cname
FROM country
EXCEPT
SELECT DISTINCT country.cid, country.cname
FROM country
NATURAL JOIN
oceanAccess
ORDER BY cname ASC;


CREATE VIEW landlocked_country_with_neighbour AS
SELECT cid AS c1id, cname AS c1name, neighbor AS c2id, ccname AS c2name
FROM
(landlocked_country INNER JOIN neighbour ON country = cid) t
INNER JOIN (SELECT cid AS ccid, cname AS ccname
FROM country) renamed_table
ON neighbor = renamed_table.ccid;

SELECT *
FROM
(SELECT c1id
FROM landlocked_country_with_neighbour
GROUP BY c1id HAVING COUNT(c2id) < 2) landlocked_country_with_one_neighbour
NATURAL JOIN landlocked_country_with_neighbour
ORDER BY c1name ASC;

DROP VIEW landlocked_country;
DROP VIEW landlocked_country_with_neighbour;
-- Query 4 statements



-- Query 5 statements



-- Query 6 statements



-- Query 7 statements



-- Query 8 statements



-- Query 9 statements



-- Query 10 statements


