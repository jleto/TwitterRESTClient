drop table staging.twitter_search cascade;
drop sequence staging.twittersearch_id_seq;

create sequence staging.twittersearch_id_seq start 1000000000;

create table staging.twitter_search
(
	id bigint not null default nextval('staging.twittersearch_id_seq'),
	tweet_id text not null,
	tweet_text text not null,
	user_id text not null,
	user_friend_count int not null,
	search_term text not null,
	constraint staging_twittersearch_pk primary key (id)
);

create unique index staging_twittersearch_unq on staging.twitter_search (tweet_id);