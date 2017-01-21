CREATE TABLE IF NOT EXISTS prefix_BANNED (
  `banned_player` VARCHAR(36) UNIQUE
);

CREATE TABLE IF NOT EXISTS prefix_TICKETS (
  `ticket_id` INT(60) NOT NULL,
  `ticket_created` BIGINT(60) NOT NULL,
  `ticket_closed` BIGINT(60),
  `ticket_author` VARCHAR(36),
  `ticket_server` VARCHAR(90),
  `ticket_location` LONGTEXT,
  `ticket_players` INT(4),
  `ticket_assigned` LONGTEXT,
  `ticket_closedby` VARCHAR(36),
  `ticket_description` LONGTEXT,
  `ticket_closereason` LONGTEXT,
  `ticket_status` TINYINT(1),
  PRIMARY KEY(ticket_id)
);

CREATE TABLE IF NOT EXISTS prefix_COMMENTS (
  `comment_id` INT(60) NOT NULL,
  `comment_ticket` INT(60) NOT NULL,
  `comment_created` BIGINT(60),
  `comment_author` VARCHAR(36),
  `comment_text` LONGTEXT,
  PRIMARY KEY(comment_id, comment_ticket)
)