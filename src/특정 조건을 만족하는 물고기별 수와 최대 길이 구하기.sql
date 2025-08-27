-- 코드를 작성해주세요
with cte as(
    select id, fish_type, case when length is null then 10 else length end as length
    from fish_info
)
SELECT count(*) as FISH_COUNT, max(length) as MAX_LENGTH, FISH_TYPE
FROM cte
GROUP BY fish_type
HAVING AVG(length) >= 33
ORDER BY fish_type ASC;

--SELECT
--    COUNT(*) AS FISH_COUNT,
--    MAX(length) AS MAX_LENGTH,
--    fish_type
--FROM (
--    SELECT
--        id,
--        fish_type,
--        CASE WHEN length IS NULL THEN 10 ELSE length END AS length
--    FROM fish_info
--) AS sub
--GROUP BY fish_type
--HAVING AVG(length) >= 33
--ORDER BY fish_type ASC;