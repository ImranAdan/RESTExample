// IN MEMORY TABLE THAT WILL BE CREATED
CREATE TABLE PUBLIC.TODO
(
  id             INT       AUTO_INCREMENT PRIMARY KEY NOT NULL,
  todo_user_id   INT                                  NOT NULL,
  todo_id        INT                                  NOT NULL,
  todo_title     VARCHAR(255)                         NOT NULL,
  todo_completed BOOLEAN DEFAULT FALSE                NOT NULL,
  todo_created   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);