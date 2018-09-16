
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     5/16/2018 3:38:17 PM                         */
/*==============================================================*/
-- CREATE SCHEMA `videodb` DEFAULT CHARACTER SET utf8 ;
/*==============================================================*/
/* Table: Comment                                               */
/*==============================================================*/
create table Comments
(
   commentID            bigint not null auto_increment,
   username             varchar(40) not null,
   videoID              bigint not null,
   content              varchar(1024) not null,
   date_comment         date not null,
   primary key (commentID)
);

/*==============================================================*/
/* Table: LikeDislike                                           */
/*==============================================================*/
create table LikeDislike
(
   ID                   bigint not null auto_increment,
   videoID              bigint,
   commentID            bigint,
   username             varchar(40) not null,
   isLike               bool not null,
   date_likeDislike     date not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table Users
(
   username             varchar(40) not null,
   password             varchar(50) not null,
   firstName            varchar(40),
   lastName             varchar(40),
   email                varchar(50) not null,
   userType             varchar(5) not null,
   blocked              bool not null,
   date_user            date not null,
   description_user     varchar(1024),
   primary key (username)
);

/*==============================================================*/
/* Table: Video                                                 */
/*==============================================================*/
create table Video
(
   videoID              bigint not null auto_increment,
   username             varchar(40) not null,
   url                  varchar(1024) not null,
   image                varchar(1024) not null,
   description          varchar(1024),
   visibility           varchar(8) not null,
   commentsEnabled      bool not null,
   ratingVisibility     bool not null,
   blocked_video        bool not null,
   views                bigint not null,
   date_video           date not null,
   deleted				bool not null,
   primary key (videoID)
);

/*==============================================================*/
/* Table: Subscriber                                            */
/*==============================================================*/
create table Subscriber
(
    id					 bigint not null auto_increment,
   subscriber            varchar(40) not null,
   subscribed            varchar(40) not null,
   
   primary key (id)
     
);

alter table Comments add constraint FK_userComment foreign key (username)
      references Users (username) on delete cascade on update restrict;

alter table Comments add constraint FK_videoComment foreign key (videoID)
      references Video (videoID) on delete cascade on update restrict;

alter table LikeDislike add constraint FK_likeDislikeComment foreign key (commentID)
      references Comments (commentID) on delete cascade on update restrict;

alter table LikeDislike add constraint FK_likeDislikeVideo foreign key (videoID)
      references Video (videoID) on delete cascade on update restrict;

alter table LikeDislike add constraint FK_userLikeDislike foreign key (username)
      references Users (username) on delete cascade on update restrict;

alter table Video add constraint FK_userVideo foreign key (username)
      references Users (username) on delete cascade on update restrict;
      
alter table Subscriber add constraint FK_userSubscriber foreign key (subscriber)
      references Users (username) on delete cascade on update restrict;
      
alter table Subscriber add constraint FK_userSubscribed foreign key (subscribed)
      references Users (username) on delete cascade on update restrict;
