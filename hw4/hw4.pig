raw = LOAD './input/tweet.txt' AS (line);
keyword = LOAD './input/kw.txt' AS (kw);

zero = FOREACH keyword GENERATE kw, 0 AS num;
h = FOREACH (FILTER raw BY (UPPER(line) matches '.*HACKATHON.*')) GENERATE 'hackathon', 1;
d = FOREACH (FILTER raw BY (UPPER(line) matches '.*DEC.*')) GENERATE 'Dec', 1;
c = FOREACH (FILTER raw BY (UPPER(line) matches '.*CHICAGO.*')) GENERATE 'Chicago',1;
j = FOREACH (FILTER raw BY (UPPER(line) matches '.*JAVA.*')) GENERATE 'Java',1;

mid = UNION zero, h, d, c, j;
grouped = GROUP mid BY $0;
res = FOREACH grouped GENERATE $0, SUM(mid.num);
DUMP res;
STORE res INTO './output' USING PigStorage(',');
