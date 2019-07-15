pragma solidity ^0.4.0;
contract RentHouseContract {

    /* Define User Struct */
    struct Account {
        string name;
        uint timestampDateOfBirth;
        string placeOfPermanent;
        string idCard;
        uint timestampCardDated;
        string phoneNumber;
        string placeOfIssueOfIdentityCard;
        bool isValid;
    }

    /* Define Room Struct */
    struct Room {
        string roomAddress;
        uint price;
        uint electricityPrice;
        uint waterPrice;
        uint square;
        string term;
        uint prepaid;
        bool isValid;
    }

    /* Define Rent Paid Struct */
    struct RentPaid {
        uint timestampCreated;
        uint timestampPaid;
        uint value;
    }

    /* Define Datas */
    uint public timestampCreated;
    bool public isActive;
    Account public owner;
    Account public renter;
    Room public room;
    uint public timestampContractStart;
    uint public timestampContractEnd;
    RentPaid[] public rentPaids;

    /* Constructor */
    // not found a way to pass a struct
    function RentHouseContract() {
    }

    /* Modifiers */

    modifier require(bool _condition) {
        if (!_condition) throw;
        _;
    }

    modifier accountNotSet(bool _isOwner) {
        if ((_isOwner && owner.isValid) || (!_isOwner && renter.isValid)) throw;
        _;
    }

    modifier roomNotSet() {
        if (room.isValid) throw;
        _;
    }

    modifier requireFullInfo() {
        if (!owner.isValid || !renter.isValid || !room.isValid) throw;
        _;
    }

    /* Getters */

    function getAddress() constant returns (address) {
        return this;
    }

    /* Setters */

    function setAccount(
        bool _isOwner,
        string _name, uint _timestampDateOfBirth, string _placeOfPermanent, string _idCard,
        uint _timestampCardDated, string _phoneNumber, string _placeOfIssueOfIdentityCard)
        accountNotSet(_isOwner)
        {
            if (_isOwner) {
                owner = Account({
                    name: _name,
                    timestampDateOfBirth: _timestampDateOfBirth,
                    placeOfPermanent: _placeOfPermanent,
                    idCard: _idCard,
                    timestampCardDated: _timestampCardDated,
                    phoneNumber: _phoneNumber,
                    placeOfIssueOfIdentityCard: _placeOfIssueOfIdentityCard,
                    isValid: true
                });
            } else {
                renter = Account({
                    name: _name,
                    timestampDateOfBirth: _timestampDateOfBirth,
                    placeOfPermanent: _placeOfPermanent,
                    idCard: _idCard,
                    timestampCardDated: _timestampCardDated,
                    phoneNumber: _phoneNumber,
                    placeOfIssueOfIdentityCard: _placeOfIssueOfIdentityCard,
                    isValid: true
                });
            }
        }

    function setRoom(string _address, uint _price, uint _electricityPrice, uint _waterPrice, uint _square, string _term, uint _prepaid)
    roomNotSet()
    {
        room = Room({
            roomAddress: _address,
            price: _price,
            electricityPrice: _electricityPrice,
            waterPrice: _waterPrice,
            square: _square,
            term: _term,
            prepaid: _prepaid,
            isValid: true
        });
    }

    /* Event Listeners */

    event contractTerminate();

    event paymentRequest(uint money);

    /* Functions */

    function addPaymentRequest(uint _timestampCreated, uint _electricBill, uint _waterBill)
    require(isActive)
    requireFullInfo()
    {
        paymentRequest(room.price + (_electricBill * room.electricityPrice) + (_waterBill * room.waterPrice));
    }

    function confirmPayment(uint _timestampCreated, uint _timestampPaid, uint _value)
    require(isActive)
    requireFullInfo()
    {
        rentPaids.push(RentPaid({
            timestampCreated: _timestampCreated,
            timestampPaid: _timestampPaid,
            value: _value
        }));
    }

    function terminateContract()
    require(isActive)
    requireFullInfo()
    {
        isActive = false;
        contractTerminate();
    }

}