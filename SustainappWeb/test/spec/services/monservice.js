'use strict';

describe('Service: monService', function () {

  // load the service's module
  beforeEach(module('sustainappWebApp'));

  // instantiate service
  var monService;
  beforeEach(inject(function (_monService_) {
    monService = _monService_;
  }));

  it('should do something', function () {
    expect(!!monService).toBe(true);
  });

});
