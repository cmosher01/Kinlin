SELECT
    R.ID,
    R.Person1ID,
    R.Person2ID,
    C.PersonID
FROM
    Relationship AS R LEFT OUTER JOIN
    ChildRelationship AS C ON (C.RelationshipID = R.ID)
ORDER BY
    R.ID
