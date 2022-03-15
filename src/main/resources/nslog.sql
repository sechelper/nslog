-- nslog.access definition

CREATE TABLE `access` (
                          `id` bigint(11) NOT NULL AUTO_INCREMENT,
                          `secret` varchar(36) NOT NULL,
                          `app_id` varchar(36) NOT NULL,
                          `user_id` varchar(36) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- nslog.access_role definition

CREATE TABLE `access_role` (
                               `id` bigint(11) NOT NULL AUTO_INCREMENT,
                               `role_id` bigint(20) unsigned NOT NULL,
                               `access_id` bigint(20) unsigned NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- nslog.record definition

CREATE TABLE `record` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `domain` varchar(256) NOT NULL,
                          `source` varchar(256) NOT NULL,
                          `type` varchar(2) NOT NULL,
                          `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;


-- nslog.`role` definition

CREATE TABLE `role` (
                        `id` bigint(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

INSERT INTO `role` (name) VALUES
                              ('USER'),
                              ('ADMIN'),
                              ('API');

-- nslog.`user` definition

CREATE TABLE `user` (
                        `id` bigint(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `source` varchar(32) DEFAULT NULL,
                        `accountNonExpired` tinyint(1) NOT NULL DEFAULT '1',
                        `accountNonLocked` tinyint(1) NOT NULL DEFAULT '1',
                        `credentialsNonExpired` tinyint(1) NOT NULL DEFAULT '1',
                        `enabled` tinyint(1) NOT NULL DEFAULT '1',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


-- nslog.user_role definition

CREATE TABLE `user_role` (
                             `user_id` bigint(11) NOT NULL,
                             `role_id` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
