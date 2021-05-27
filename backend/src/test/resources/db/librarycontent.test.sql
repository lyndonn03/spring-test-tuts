INSERT INTO song VALUES ('8bc5c0bc-ce44-4596-895c-a33001819175', 'Test Title #1', 'Test Artist #1'),
                        ('80eb39b6-9fea-4270-a955-d6cde472963f', 'Test Title #2', 'Test Artist #2'),
                        ('fa9b7d98-2a8c-4be6-848f-2c5953fb51a4', 'Test Title #3', 'Test Artist #3');


INSERT INTO library (id, name) VALUES ('36ab2b24-a27d-4d58-a92f-bc4cdd96853b', 'TEST LIBRARY #1'),
                           ('4e4fb99b-a6b1-45e4-a3ed-085600642248', 'TEST LIBRARY #2');


INSERT INTO library_content VALUES ('36ab2b24-a27d-4d58-a92f-bc4cdd96853b', 'fa9b7d98-2a8c-4be6-848f-2c5953fb51a4'),
                                ('36ab2b24-a27d-4d58-a92f-bc4cdd96853b', '80eb39b6-9fea-4270-a955-d6cde472963f'),
                                ('4e4fb99b-a6b1-45e4-a3ed-085600642248', '8bc5c0bc-ce44-4596-895c-a33001819175');