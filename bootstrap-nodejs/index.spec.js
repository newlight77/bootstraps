const compute = require("./index");

describe("Basic test", () => {
  it("should do something when having some inputs", () => {
    // Arrange
    const input = "1";
    const output = "1";

    // Act
    const result = compute(input);

    // Assert
    expect(result).toEqual(output);
  });
});
