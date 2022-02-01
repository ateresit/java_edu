SELECT * FROM my_cinema.tickets;

use my_cinema;

select movie.title, sessions.session_start, movie.duration, sessions.session_date, breake_time.pause_time
from sessions
left join movie on movie.id = sessions.movie_id
left join breake_time on breake_time.id = sessions.session_braketime_id
where breake_time.pause_time > '1000-01-01 00:30:00'
order by breake_time.pause_time;

# -- код с урока - сам такое сделать не смогу на данном этапе (COMMON TABLE EXPRESSION) --- задание 1 ----
WITH intervals AS (
	SELECT SM.movie_id,
		SM.id AS session_id,
        MV.title,
        SM.session_start,
        DATE_ADD(SM.session_start, INTERVAL MV.duration MINUTE) AS end_time
    FROM sessions SM
    INNER JOIN movie MV ON SM.movie_id = MV.id)
SELECT I1.session_id, I2.session_id,
		I1.title, I1.session_start, I1.end_time,
        I2.title, I2.session_start, I2.end_time
FROM intervals I1
	INNER JOIN intervals I2 ON I1.session_start < I2.end_time AND I1.end_time > I2.session_start
				AND I1.session_id <> I2.session_id AND I1.session_id < I2.session_id;
                
# -- код с урока, задание 2 -------
WITH intervals AS (
	SELECT SM.movie_id,
		SM.id AS session_id,
        MV.title,
        SM.session_start,
        DATE_ADD(SM.session_start, INTERVAL MV.duration MINUTE) AS end_time
    FROM sessions SM
    INNER JOIN movie MV ON SM.movie_id = MV.id)
SELECT 	I1.title, I1.session_start, I1.end_time,
        I2.title, I2.session_start, I2.end_time
        TIMESTAMPDIFF(MINUTE, I1.end_time, I2.session_start)
FROM intervals I1
	INNER JOIN intervals I2 ON I1.end_time < I2.session_start;