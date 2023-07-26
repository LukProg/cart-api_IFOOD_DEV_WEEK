INSERT INTO restaurant (id, zip_code, additional_information, name) VALUES
                                                                      (1L, '0000001', 'Restaurant 1 Address AdditionalInformation', 'Restaurant 1'),
                                                                      (2L, '0000002', 'Restaurant 2 Address AdditionalInformation', 'Restaurant 2');

INSERT INTO client (id, zip_code, additional_information, name) VALUES
    (1L, '0000001', 'Client 1 Address AdditionalInformation', 'Client 1');

INSERT INTO product (id, available, name, unit_value, restaurant_id) VALUES
                                                                         (1L, true, 'Product 1', 5.0, 1L),
                                                                         (2L, true, 'Product 2', 6.0, 1L),
                                                                         (3L, true, 'Product 3', 7.0, 2L);

INSERT INTO cart (id, payment_method, closed,  total_value, client_id) VALUES
    (1L, 0, false, 0.0, 1L);